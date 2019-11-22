package com.ms_snhu.springbootmongodbsecurity.controller;

import java.util.List;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ms_snhu.springbootmongodbsecurity.controller.helpers.ControllerHelper;
import com.ms_snhu.springbootmongodbsecurity.domain.SectorIndustryOutstandingShares;
import com.ms_snhu.springbootmongodbsecurity.domain.Stocks;

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

	
	@Value("${spring.session.mongodb.collection-name}")
	private String collectionName;
	@Value("${snhu.studentName}")
	private String studentName;
	@Value("${snhu.dateFormat}")
	private String dateFormat;

	/*
	@GetMapping("/dashboard")
	public String index(
			@RequestParam(name = "name", required = false, defaultValue = "${snhu.studentName}") String name,
			Model model) {
		List<String> industries = marketHelper
				.convertIterableToList(marketTemplate.getCollection(collectionName).distinct("Industry", String.class));
		List<String> sectors = marketHelper
				.convertIterableToList(marketTemplate.getCollection(collectionName).distinct("Sector", String.class));

		model.addAttribute("industries", industries);
		model.addAttribute("sectors", sectors);
		model.addAttribute("name", name);
		model.addAttribute("isFiftyDay", false);
		model.addAttribute("isIndustry", false);
		return "dashboard.html";
	}
*/
	@GetMapping("/industryPerformance")
	public String industryPerformance(@RequestParam(name = "industry", required = true) String industry,
			@RequestParam(name = "performers", required = true) Integer performers, Model model) {

		List<String> industries = marketHelper
				.convertIterableToList(marketTemplate.getCollection(collectionName).distinct("Industry", String.class));

		Sort performanceSort = new Sort(Sort.Direction.DESC, "Performance (YTD)");
		Query industryQuery = new Query(Criteria.where("Industry").is(industry)).with(performanceSort)
				.limit(performers);
		List<Stocks> stocks = marketTemplate.find(industryQuery, Stocks.class, collectionName);

		model.addAttribute("industries", industries);
		model.addAttribute("industry", industry);
		model.addAttribute("performers", performers);
		model.addAttribute("name", studentName);
		model.addAttribute("stocks", stocks);

		model.addAttribute("isIndustry", true);
		model.addAttribute("isFiftyDay", false);
		return "dashboard.html";
	}

	@GetMapping("/fiftyDayMovingAverage")
	public String movingAverage(@RequestParam(name = "lowerBound", required = true) Double lowerBound,
			@RequestParam(name = "upperBound", required = true) Double upperBound, Model model) {

		Sort smaSort = new Sort(Sort.Direction.ASC, "50-Day Simple Moving Average");
		Query fiftyDayQuery = new Query(Criteria.where("50-Day Simple Moving Average").gt(lowerBound).lt(upperBound))
				.with(smaSort);
		List<Stocks> stocks = marketTemplate.find(fiftyDayQuery, Stocks.class, collectionName);

		model.addAttribute("stocks", stocks);
		model.addAttribute("lowerBound", lowerBound);
		model.addAttribute("upperBound", upperBound);
		model.addAttribute("name", studentName);

		model.addAttribute("isFiftyDay", true);
		model.addAttribute("isIndustry", false);
		return "dashboard.html";
	}

	@GetMapping("/insertNewStock")
	public String insertNewStock(Model model) {
		Stocks stock = new Stocks();
		stock.set_id(ObjectId.get());
		model.addAttribute("stock", stock);

		model.addAttribute("dateFormat", dateFormat);
		model.addAttribute("insertUpdate", "Insert");
		model.addAttribute("name", studentName);
		return "insertUpdate.html";
	}

	@GetMapping("/updateStock")
	public String updateStock(@RequestParam(name = "ticker", required = true) String ticker, Model model) {
		Query query = new Query(Criteria.where("ticker").is(ticker));
		Stocks stock = marketTemplate.findOne(query, Stocks.class);
		model.addAttribute("stock", stock);

		model.addAttribute("dateFormat", dateFormat);
		model.addAttribute("insertUpdate", "Update");
		model.addAttribute("name", studentName);
		return "insertUpdate.html";
	}

	/**
	 * sectorAggregation accepts the parameter to create and run a mongoDB
	 * Aggregation query getting the total number of outstanding shares for each
	 * industry in the specified sector
	 * 
	 * @param sectorName Sector to aggregate
	 * @param model Model Object populated for view
	 * @return
	 */
	@GetMapping("/sectorOutstandingShares")
	private String sectorAggregation(@RequestParam(name = "sector", required = true) String sectorName, Model model) {
		/* Create the match Operation */
		MatchOperation sectorMatchOp = Aggregation.match(new Criteria("Sector").is(sectorName));
		/* Create the group Operation */
		GroupOperation industryGroupOp = Aggregation.group("Industry").sum("Shares Outstanding").as("total");

		/* Create the aggregation combining the mating and grouping operations */
		Aggregation sectorIndustryAggregation = Aggregation.newAggregation(sectorMatchOp, industryGroupOp);

		/* Run the Aggregation */
		AggregationResults<SectorIndustryOutstandingShares> aggResults = marketTemplate
				.aggregate(sectorIndustryAggregation, collectionName, SectorIndustryOutstandingShares.class);
		List<SectorIndustryOutstandingShares> sharesResults = aggResults.getMappedResults();

		model.addAttribute("sectorName", sectorName);
		model.addAttribute("name", studentName);
		model.addAttribute("outstandingShares", sharesResults);

		return "outstandingShares.html";
	}

}
