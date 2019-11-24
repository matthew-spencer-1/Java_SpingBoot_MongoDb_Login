package com.ms_snhu.springbootmongodbsecurity.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ms_snhu.springbootmongodbsecurity.controller.helpers.ControllerHelper;
import com.ms_snhu.springbootmongodbsecurity.domain.SectorIndustryOutstandingShares;
import com.ms_snhu.springbootmongodbsecurity.domain.Stocks;
import com.ms_snhu.springbootmongodbsecurity.domain.User;
import com.ms_snhu.springbootmongodbsecurity.repository.MarketRepository;
import com.ms_snhu.springbootmongodbsecurity.repository.RoleRepository;

/**
 * Controller Class for HTTP web services
 * 
 * Enables handling of URLs to specific functionality working with the
 * MarketTemplate object
 * 
 * @author matthewspencer
 *
 */
@Controller
public class MarketWebController {
	@Autowired
	private MongoTemplate marketTemplate;
	@Autowired
	private ControllerHelper marketHelper;
	@Autowired
	private MarketRepository marketRepository;
	@Autowired
	private RoleRepository roleRpository;

	@Value("${snhu.dateFormat}")
	private String dateFormat;

	@GetMapping(value = {"/dashboard","/userView"})
    public ModelAndView dashboard() {
        ModelAndView modelAndView = new ModelAndView();
        
        String stocksCollectionName = marketTemplate.getCollectionName(Stocks.class);
        
        List<String> industries = marketHelper
				.convertIterableToList(marketTemplate.getCollection(stocksCollectionName).distinct("Industry", String.class));
		List<String> sectors = marketHelper
				.convertIterableToList(marketTemplate.getCollection(stocksCollectionName).distinct("Sector", String.class));
        
        User user = marketHelper.getAuthenticatedUser();
        modelAndView.addObject("currentUser", user);
        modelAndView.addObject("fullName", user.getFullname());
        modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
        modelAndView.addObject("industries",industries);
        modelAndView.addObject("sectors",sectors);
        modelAndView.addObject("isFiftyDay",false);
        modelAndView.addObject("isIndustry",false);
        if(user.getRoles().contains(roleRpository.findByRole("ADMIN"))) {
        	modelAndView.setViewName("dashboard");
        } else {
        	modelAndView.setViewName("userView");
        }
        return modelAndView;
    }
	
	
	@GetMapping("/industryPerformance")
	public String industryPerformance(@RequestParam(name = "industry", required = true) String industry,
			@RequestParam(name = "performers", required = true) Integer performers, Model model) {

		String stocksCollectionName = marketTemplate.getCollectionName(Stocks.class);
		
		List<String> industries = marketHelper
				.convertIterableToList(marketTemplate.getCollection(stocksCollectionName).distinct("Industry", String.class));

		Sort performanceSort = new Sort(Sort.Direction.DESC, "Performance (YTD)");
		Query industryQuery = new Query(Criteria.where("Industry").is(industry)).with(performanceSort)
				.limit(performers);
		List<Stocks> stocks = marketTemplate.find(industryQuery, Stocks.class, stocksCollectionName);

		User user = marketHelper.getAuthenticatedUser();

		model.addAttribute("industries", industries);
		model.addAttribute("industry", industry);
		model.addAttribute("performers", performers);
		model.addAttribute("currentUser", user);
		model.addAttribute("fullName", user.getFullname());
		model.addAttribute("stocks", stocks);

		model.addAttribute("isIndustry", true);
		model.addAttribute("isFiftyDay", false);
		return "dashboard.html";
	}

	@GetMapping("/fiftyDayMovingAverage")
	public String movingAverage(@RequestParam(name = "lowerBound", required = true) Double lowerBound,
			@RequestParam(name = "upperBound", required = true) Double upperBound, Model model) {

		String stocksCollectionName = marketTemplate.getCollectionName(Stocks.class);
		
		Sort smaSort = new Sort(Sort.Direction.ASC, "50-Day Simple Moving Average");
		Query fiftyDayQuery = new Query(Criteria.where("50-Day Simple Moving Average").gt(lowerBound).lt(upperBound))
				.with(smaSort);
		List<Stocks> stocks = marketTemplate.find(fiftyDayQuery, Stocks.class, stocksCollectionName);

		User user = marketHelper.getAuthenticatedUser();

		model.addAttribute("stocks", stocks);
		model.addAttribute("lowerBound", lowerBound);
		model.addAttribute("upperBound", upperBound);
		model.addAttribute("currentUser", user);
		model.addAttribute("fullName", user.getFullname());

		model.addAttribute("isFiftyDay", true);
		model.addAttribute("isIndustry", false);
		return "dashboard.html";
	}

	@GetMapping("/insertNewStock")
	public ModelAndView insertNewStock(Model model) {

		User user = marketHelper.getAuthenticatedUser();
		Stocks stock = new Stocks();
		stock.set_id(ObjectId.get().toString());
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("currentUser",user);
		mv.addObject("stock", stock);
		mv.addObject("dateFormat", dateFormat);
		mv.addObject("insertUpdate", "Insert");
		mv.addObject("fullName", user.getFullname());
		
		mv.setViewName("insertUpdate");
		
		return mv;
	}

	@GetMapping("/updateStock")
	public String updateStock(@RequestParam(name = "ticker", required = true) String ticker, Model model) {

		User user = marketHelper.getAuthenticatedUser();
		Query query = new Query(Criteria.where("ticker").is(ticker));
		Stocks stock = marketTemplate.findOne(query, Stocks.class);
		model.addAttribute("stock", stock);

		model.addAttribute("currentUser", user);
		model.addAttribute("dateFormat", dateFormat);
		model.addAttribute("insertUpdate", "Update");
		model.addAttribute("fullName", user.getFullname());
		return "insertUpdate.html";
	}

	/**
	 * sectorAggregation accepts the parameter to create and run a mongoDB
	 * Aggregation query getting the total number of outstanding shares for each
	 * industry in the specified sector
	 * 
	 * @param sectorName Sector to aggregate
	 * @param model      Model Object populated for view
	 * @return
	 */
	@GetMapping("/sectorOutstandingShares")
	private String sectorAggregation(@RequestParam(name = "sector", required = true) String sectorName, Model model) {
		String stocksCollectionName = marketTemplate.getCollectionName(Stocks.class);
		
		/* Create the match Operation */
		MatchOperation sectorMatchOp = Aggregation.match(new Criteria("Sector").is(sectorName));
		/* Create the group Operation */
		GroupOperation industryGroupOp = Aggregation.group("Industry").sum("Shares Outstanding").as("total");

		/* Create the aggregation combining the mating and grouping operations */
		Aggregation sectorIndustryAggregation = Aggregation.newAggregation(sectorMatchOp, industryGroupOp);

		/* Run the Aggregation */
		AggregationResults<SectorIndustryOutstandingShares> aggResults = marketTemplate
				.aggregate(sectorIndustryAggregation, stocksCollectionName, SectorIndustryOutstandingShares.class);
		List<SectorIndustryOutstandingShares> sharesResults = aggResults.getMappedResults();
		
		User user = marketHelper.getAuthenticatedUser();

		model.addAttribute("currentUser", user);
		model.addAttribute("sectorName", sectorName);
		model.addAttribute("fullName", user.getFullname());
		model.addAttribute("outstandingShares", sharesResults);

		return "outstandingShares.html";
	}
	
	/**
	 * method to facilitate the uploading of data contained in csv files to the stocks collection
	 * 
	 * @param uploadfile
	 * @param fromWeb
	 * @param httpServletResponse
	 * @return
	 */
	@RequestMapping(value = "/UploadCsvFile", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	public ModelAndView updloadStocksCsv(@RequestParam("file") MultipartFile uploadfile,
			@RequestParam(name = "fromWeb", required = false) Optional<Boolean> fromWeb,
			HttpServletResponse httpServletResponse) {
		
		List<Stocks> stocksToSave = null;
		try {
			stocksToSave = marketHelper.convertCSVToStocks(uploadfile.getInputStream());
			
			marketRepository.insert(stocksToSave);
		} catch (IOException | ParseException e) {
			marketHelper.redirectToError(httpServletResponse, e.getMessage());
		}
		
		return this.dashboard();
	}

}
