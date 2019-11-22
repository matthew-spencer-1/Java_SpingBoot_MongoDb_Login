package com.ms_snhu.springbootmongodbsecurity.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ms_snhu.springbootmongodbsecurity.controller.helpers.ControllerHelper;
import com.ms_snhu.springbootmongodbsecurity.domain.Stocks;
import com.ms_snhu.springbootmongodbsecurity.repository.MarketRepository;

/**
 * RESTful web services controller for the Market repository
 * 
 * @author matthewspencer
 *
 */
@RestController
public class MarketRestController {

	@Autowired
	private MarketRepository marketRepository;
	@Autowired
	private ControllerHelper marketHelper;

	@RequestMapping("/pullAll")
	public List<Stocks> getAll() {
		return marketRepository.findAll();
	}

	@RequestMapping(value = "/Insert", method = RequestMethod.POST)
	public Stocks createStock(@Valid @RequestBody Stocks stockToCreate) {
		if (StringUtils.isEmpty(stockToCreate.get_id())) {
			stockToCreate.set_id(ObjectId.get());
		}

		marketRepository.insert(stockToCreate);

		return stockToCreate;
	}

	@RequestMapping(value = "/InsertMultiple", method = RequestMethod.POST)
	public List<Stocks> createMultipleStocks(@Valid @RequestBody List<Stocks> stocksToCreate) {
		for (Stocks stock : stocksToCreate) {
			if (StringUtils.isEmpty(stock.get_id())) {
				stock.set_id(ObjectId.get());
			}
		}

		marketRepository.insert(stocksToCreate);

		return stocksToCreate;
	}

	@RequestMapping(value = "/UploadFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Stocks> uploadStocksFile(@RequestParam("file") MultipartFile uploadfile,
			@RequestParam(name = "fromWeb", required = false) Optional<Boolean> fromWeb,
			HttpServletResponse httpServletResponse) {
		List<Stocks> stocksToSave = null;
		try {
			stocksToSave = marketHelper.convertJSONToStocks(uploadfile.getInputStream());
		} catch (IOException e) {
			this.redirectToError(httpServletResponse, e.getLocalizedMessage());
		}
		if (fromWeb.isPresent() && fromWeb.get()) {
			this.redirectToDashboard(httpServletResponse);
		}

		marketRepository.saveAll(stocksToSave);

		return stocksToSave;
	}

	@RequestMapping(value = "/Update", method = RequestMethod.POST)
	public Stocks updateStockValue(@Valid @RequestBody Stocks stockToUpdate) {
		marketRepository.save(stockToUpdate);

		return stockToUpdate;
	}

	@RequestMapping(value = "/UpdateVolume")
	public Stocks updateVolumeForTicker(@RequestParam("Ticker") String ticker, @RequestParam("Volume") Double volume) {
		Stocks stockToUpdate = marketRepository.findByTicker(ticker);
		if (null != stockToUpdate) {
			stockToUpdate.setVolume(volume);
			marketRepository.save(stockToUpdate);
		}

		return stockToUpdate;
	}

	@RequestMapping(value = "/delete")
	public void deleteRecord(@RequestParam("Ticker") String ticker,
			@RequestParam(name = "fromWeb", required = false) Optional<Boolean> fromWeb,
			HttpServletResponse httpServletResponse) {
		Stocks stockToDelete = marketRepository.findByTicker(ticker);
		if (null == stockToDelete) {
			this.redirectToError(httpServletResponse, "Unable to find Stock with ticker Id: " + ticker + ".");
		} else {
			marketRepository.delete(stockToDelete);
			if (fromWeb.isPresent() && fromWeb.get()) {
				this.redirectToDashboard(httpServletResponse);
			}
		}
	}

	@RequestMapping("/ticker")
	public Stocks getByTickerId(@RequestParam("id") String tickerId) {
		return marketRepository.findByTicker(tickerId);
	}

	@RequestMapping("/company")
	public List<Stocks> getByCompanyName(@RequestParam("name") String companyName) {

		return marketRepository.findByCompanyName(companyName);
	}

	@RequestMapping("/topFive")
	public List<Stocks> getTopFiveInIndustry(@RequestParam("industry") String industryType) {

		return marketRepository.findTop5ByIndustryInOrderByPerformanceYTDDesc(industryType);
	}

	/**
	 * Trigger redirect from RESTful service to http web service dashboard
	 * 
	 * @param httpServletResponse
	 */
	private void redirectToDashboard(HttpServletResponse httpServletResponse) {
		httpServletResponse.setHeader("Location", "/dashboard");
		httpServletResponse.setStatus(302);
	}

	/**
	 * Trigger redirect from RESTful service to http web service error page
	 * 
	 * @param httpServletResponse
	 * @param messageText
	 */
	private void redirectToError(HttpServletResponse httpServletResponse, String messageText) {
		httpServletResponse.setHeader("Location", "/error?message=" + messageText);
		httpServletResponse.setStatus(302);
	}
}
