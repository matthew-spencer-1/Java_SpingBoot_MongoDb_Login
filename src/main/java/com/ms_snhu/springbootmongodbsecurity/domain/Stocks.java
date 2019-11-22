package com.ms_snhu.springbootmongodbsecurity.domain;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model class for the Stocks mongoDB collection
 * 
 * @author matthewspencer
 *
 */
@JsonInclude(Include.NON_EMPTY)
public class Stocks {
	
	@Value("${snhu.dateFormat}")
	private String dateFormat;

	@Id
	private ObjectId _id;

	@Field("Ticker")
	@JsonProperty("Ticker")
	private String ticker;
	@Field("Company")
	@JsonProperty("Company")
	private String companyName;
	@Field("Sector")
	@JsonProperty("Sector")
	private String sector;
	@Field("Industry")
	@JsonProperty("Industry")
	private String industry;
	@Field("Country")
	@JsonProperty("Country")
	private String country;
	
	@Field("Earnings Date")
	@JsonProperty("Earnings Date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM dd yyyy")
	private Date earningsDate;

	@Field("Profit Margin")
	@JsonProperty("Profit Margin")
	private Double profitMargin;
	@Field("Institutional Ownership")
	@JsonProperty("Institutional Ownership")
	private Double institutionalOwnership;
	@Field("EPS growth past 5 years")
	@JsonProperty("EPS growth past 5 years")
	private Double eps5yeargrowth;
	@Field("Total Debt/Equity")
	@JsonProperty("Total Debt/Equity")
	private Double totalDebtEquity;
	@Field("Current Ratio")
	@JsonProperty("Current Ratio")
	private Double currentRatio;
	@Field("Return on Assets")
	@JsonProperty("Return on Assets")
	private Double returnOnAssets;
	@Field("P/S")
	@JsonProperty("P/S")
	private Double ps;
	@Field("Change from Open")
	@JsonProperty("Change from Open")
	private Double changeFromOpen;
	@Field("Performance (YTD)")
	@JsonProperty("Performance (YTD)")
	private Double performanceYTD;
	@Field("Performance (Week)")
	@JsonProperty("Performance (Week)")
	private Double performanceWeek;
	@Field("Quick Ratio")
	@JsonProperty("Quick Ratio")
	private Double quickRatio;
	@Field("Insider Transactions")
	@JsonProperty("Insider Transactions")
	private Double insiderTransactions;
	@Field("P/B")
	@JsonProperty("P/B")
	private Double pb;
	@Field("EPS growth quarter over quarter")
	@JsonProperty("EPS growth quarter over quarter")
	private Double epsGrowthQuarterOver;
	@Field("Payout Ratio")
	@JsonProperty("Payout Ratio")
	private Double payoutRatio;
	@Field("Performance (Quarter)")
	@JsonProperty("Performance (Quarter)")
	private Double performanceQuarter;
	@Field("Forward P/E")
	@JsonProperty("Forward P/E")
	private Double forwardPe;
	@Field("P/E")
	@JsonProperty("P/E")
	private Double pe;
	@Field("200-Day Simple Moving Average")
	@JsonProperty("200-Day Simple Moving Average")
	private Double simpleMovingAverage200Day;
	@Field("Shares Outstanding")
	@JsonProperty("Shares Outstanding")
	private Double sharesOutstanding;
	@Field("52-Week High")
	@JsonProperty("52-Week High")
	private Double high52Week;
	@Field("P/Cash")
	@JsonProperty("P/Cash")
	private Double pCash;
	@Field("Change")
	@JsonProperty("Change")
	private Double change;
	@Field("Analyst Recom")
	@JsonProperty("Analyst Recom")
	private Double analyistRecom;
	@Field("Volatility (Week)")
	@JsonProperty("Volatility (Week)")
	private Double volatilityWeek;
	@Field("Return on Equity")
	@JsonProperty("Return on Equity")
	private Double returnOnEquity;
	@Field("50-Day Low")
	@JsonProperty("50-Day Low")
	private Double low50Day;
	@Field("Price")
	@JsonProperty("Price")
	private Double price;
	@Field("50-Day High")
	@JsonProperty("50-Day High")
	private Double high50Day;
	@Field("Return on Investment")
	@JsonProperty("Return on Investment")
	private Double returnOnInvestment;
	@Field("Shares Float")
	@JsonProperty("Shares Float")
	private Double sharesFloat;
	@Field("Dividend Yield")
	@JsonProperty("Dividend Yield")
	private Double dividendYield;
	@Field("EPS growth next 5 years")
	@JsonProperty("EPS growth next 5 years")
	private Double epsGrowth5years;
	@Field("Beta")
	@JsonProperty("Beta")
	private Double beta;
	@Field("Sales growth quarter over quarter")
	@JsonProperty("Sales growth quarter over quarter")
	private Double salesGrowthQuarterOver;
	@Field("Operating Margin")
	@JsonProperty("Operating Margin")
	private Double operatingMargin;
	@Field("EPS (ttm)")
	@JsonProperty("EPS (ttm)")
	private Double epsTtm;
	@Field("PEG")
	@JsonProperty("PEG")
	private Double peg;
	@Field("Float Short")
	@JsonProperty("Float Short")
	private Double floatShort;
	@Field("52-Week Low")
	@JsonProperty("52-Week Low")
	private Double low52week;
	@Field("Average True Range")
	@JsonProperty("Average True Range")
	private Double averageTrueRange;
	@Field("EPS growth next year")
	@JsonProperty("EPS growth next year")
	private Double epsGrowthOneYear;
	@Field("Sales growth past 5 years")
	@JsonProperty("Sales growth past 5 years")
	private Double salesGrowthPastFiveYears;
	@Field("Gap")
	@JsonProperty("Gap")
	private Double gap;
	@Field("Relative Volume")
	@JsonProperty("Relative Volume")
	private Double relativeVolume;
	@Field("Volatility (Month)")
	@JsonProperty("Volatility (Month)")
	private Double volatilityMonth;
	@Field("Market Cap")
	@JsonProperty("Market Cap")
	private Double marketCap;
	@Field("Volume")
	@JsonProperty("Volume")
	private Double volume;
	@Field("Gross Margin")
	@JsonProperty("Gross Margin")
	private Double grossMargin;
	@Field("Short Ratio")
	@JsonProperty("Short Ratio")
	private Double shortRatio;
	@Field("Performance (Half Year)")
	@JsonProperty("Performance (Half Year)")
	private Double performanceHalfYear;
	@Field("Relative Strength Index (14)")
	@JsonProperty("Relative Strength Index (14)")
	private Double rsi14;
	@Field("Insider Ownership")
	@JsonProperty("Insider Ownership")
	private Double insiderOwnership;
	@Field("20-Day Simple Moving Average")
	@JsonProperty("20-Day Simple Moving Average")
	private Double simpleMovingAverage20day;
	@Field("Performance (Month)")
	@JsonProperty("Performance (Month)")
	private Double performanceMonth;
	@Field("P/Free Cash Flow")
	@JsonProperty("P/Free Cash Flow")
	private Double pFreeCashFlow;
	@Field("Institutional Transactions")
	@JsonProperty("Institutional Transactions")
	private Double institutionalTransactions;
	@Field("Performance (Year)")
	@JsonProperty("Performance (Year)")
	private Double performanceYear;
	@Field("LT Debt/Equity")
	@JsonProperty("LT Debt/Equity")
	private Double ltDebtEquity;
	@Field("Average Volume")
	@JsonProperty("Average Volume")
	private Double averageVolume;
	@Field("EPS growth this year")
	@JsonProperty("EPS growth this year")
	private Double epsGrowthYear;
	@Field("50-Day Simple Moving Average")
	@JsonProperty("50-Day Simple Moving Average")
	private Double simpleMovingAverage50day;

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Double getPerformanceYTD() {
		return performanceYTD;
	}

	public void setPerformanceYTD(Double performanceYTD) {
		this.performanceYTD = performanceYTD;
	}

	public Double getSimpleMovingAverage50day() {
		return simpleMovingAverage50day;
	}

	public void setSimpleMovingAverage50day(Double simpleMovingAverage50day) {
		this.simpleMovingAverage50day = simpleMovingAverage50day;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Double getProfitMargin() {
		return profitMargin;
	}

	public void setProfitMargin(Double profitMargin) {
		this.profitMargin = profitMargin;
	}

	public Double getInstitutionalOwnership() {
		return institutionalOwnership;
	}

	public void setInstitutionalOwnership(Double institutionalOwnership) {
		this.institutionalOwnership = institutionalOwnership;
	}

	public Double getEps5yeargrowth() {
		return eps5yeargrowth;
	}

	public void setEps5yeargrowth(Double eps5yeargrowth) {
		this.eps5yeargrowth = eps5yeargrowth;
	}

	public Double getTotalDebtEquity() {
		return totalDebtEquity;
	}

	public void setTotalDebtEquity(Double totalDebtEquity) {
		this.totalDebtEquity = totalDebtEquity;
	}

	public Double getCurrentRatio() {
		return currentRatio;
	}

	public void setCurrentRatio(Double currentRatio) {
		this.currentRatio = currentRatio;
	}

	public Double getReturnOnAssets() {
		return returnOnAssets;
	}

	public void setReturnOnAssets(Double returnOnAssets) {
		this.returnOnAssets = returnOnAssets;
	}

	public Double getPs() {
		return ps;
	}

	public void setPs(Double ps) {
		this.ps = ps;
	}

	public Double getChangeFromOpen() {
		return changeFromOpen;
	}

	public void setChangeFromOpen(Double changeFromOpen) {
		this.changeFromOpen = changeFromOpen;
	}

	public Double getPerformanceWeek() {
		return performanceWeek;
	}

	public void setPerformanceWeek(Double performanceWeek) {
		this.performanceWeek = performanceWeek;
	}

	public Double getQuickRatio() {
		return quickRatio;
	}

	public void setQuickRatio(Double quickRatio) {
		this.quickRatio = quickRatio;
	}

	public Double getInsiderTransactions() {
		return insiderTransactions;
	}

	public void setInsiderTransactions(Double insiderTransactions) {
		this.insiderTransactions = insiderTransactions;
	}

	public Double getPb() {
		return pb;
	}

	public void setPb(Double pb) {
		this.pb = pb;
	}

	public Double getEpsGrowthQuarterOver() {
		return epsGrowthQuarterOver;
	}

	public void setEpsGrowthQuarterOver(Double epsGrowthQuarterOver) {
		this.epsGrowthQuarterOver = epsGrowthQuarterOver;
	}

	public Double getPayoutRatio() {
		return payoutRatio;
	}

	public void setPayoutRatio(Double payoutRatio) {
		this.payoutRatio = payoutRatio;
	}

	public Double getPerformanceQuarter() {
		return performanceQuarter;
	}

	public void setPerformanceQuarter(Double performanceQuarter) {
		this.performanceQuarter = performanceQuarter;
	}

	public Double getForwardPe() {
		return forwardPe;
	}

	public void setForwardPe(Double forwardPe) {
		this.forwardPe = forwardPe;
	}

	public Double getPe() {
		return pe;
	}

	public void setPe(Double pe) {
		this.pe = pe;
	}

	public Double getSimpleMovingAverage200Day() {
		return simpleMovingAverage200Day;
	}

	public void setSimpleMovingAverage200Day(Double simpleMovingAverage200Day) {
		this.simpleMovingAverage200Day = simpleMovingAverage200Day;
	}

	public Double getSharesOutstanding() {
		return sharesOutstanding;
	}

	public void setSharesOutstanding(Double sharesOutstanding) {
		this.sharesOutstanding = sharesOutstanding;
	}

	public Date getEarningsDate() {
		return earningsDate;
	}

	public void setEarningsDate(Date earningsDate) {
		this.earningsDate = earningsDate;
	}

	public Double getHigh52Week() {
		return high52Week;
	}

	public void setHigh52Week(Double high52Week) {
		this.high52Week = high52Week;
	}

	public Double getpCash() {
		return pCash;
	}

	public void setpCash(Double pCash) {
		this.pCash = pCash;
	}

	public Double getChange() {
		return change;
	}

	public void setChange(Double change) {
		this.change = change;
	}

	public Double getAnalyistRecom() {
		return analyistRecom;
	}

	public void setAnalyistRecom(Double analyistRecom) {
		this.analyistRecom = analyistRecom;
	}

	public Double getVolatilityWeek() {
		return volatilityWeek;
	}

	public void setVolatilityWeek(Double volatilityWeek) {
		this.volatilityWeek = volatilityWeek;
	}

	public Double getReturnOnEquity() {
		return returnOnEquity;
	}

	public void setReturnOnEquity(Double returnOnEquity) {
		this.returnOnEquity = returnOnEquity;
	}

	public Double getLow50Day() {
		return low50Day;
	}

	public void setLow50Day(Double low50Day) {
		this.low50Day = low50Day;
	}

	public Double getHigh50Day() {
		return high50Day;
	}

	public void setHigh50Day(Double high50Day) {
		this.high50Day = high50Day;
	}

	public Double getReturnOnInvestment() {
		return returnOnInvestment;
	}

	public void setReturnOnInvestment(Double returnOnInvestment) {
		this.returnOnInvestment = returnOnInvestment;
	}

	public Double getSharesFloat() {
		return sharesFloat;
	}

	public void setSharesFloat(Double sharesFloat) {
		this.sharesFloat = sharesFloat;
	}

	public Double getDividendYield() {
		return dividendYield;
	}

	public void setDividendYield(Double dividendYield) {
		this.dividendYield = dividendYield;
	}

	public Double getEpsGrowth5years() {
		return epsGrowth5years;
	}

	public void setEpsGrowth5years(Double epsGrowth5years) {
		this.epsGrowth5years = epsGrowth5years;
	}

	public Double getBeta() {
		return beta;
	}

	public void setBeta(Double beta) {
		this.beta = beta;
	}

	public Double getSalesGrowthQuarterOver() {
		return salesGrowthQuarterOver;
	}

	public void setSalesGrowthQuarterOver(Double salesGrowthQuarterOver) {
		this.salesGrowthQuarterOver = salesGrowthQuarterOver;
	}

	public Double getOperatingMargin() {
		return operatingMargin;
	}

	public void setOperatingMargin(Double operatingMargin) {
		this.operatingMargin = operatingMargin;
	}

	public Double getEpsTtm() {
		return epsTtm;
	}

	public void setEpsTtm(Double epsTtm) {
		this.epsTtm = epsTtm;
	}

	public Double getPeg() {
		return peg;
	}

	public void setPeg(Double peg) {
		this.peg = peg;
	}

	public Double getFloatShort() {
		return floatShort;
	}

	public void setFloatShort(Double floatShort) {
		this.floatShort = floatShort;
	}

	public Double getLow52week() {
		return low52week;
	}

	public void setLow52week(Double low52week) {
		this.low52week = low52week;
	}

	public Double getAverageTrueRange() {
		return averageTrueRange;
	}

	public void setAverageTrueRange(Double averageTrueRange) {
		this.averageTrueRange = averageTrueRange;
	}

	public Double getEpsGrowthOneYear() {
		return epsGrowthOneYear;
	}

	public void setEpsGrowthOneYear(Double epsGrowthOneYear) {
		this.epsGrowthOneYear = epsGrowthOneYear;
	}

	public Double getSalesGrowthPastFiveYears() {
		return salesGrowthPastFiveYears;
	}

	public void setSalesGrowthPastFiveYears(Double salesGrowthPastFiveYears) {
		this.salesGrowthPastFiveYears = salesGrowthPastFiveYears;
	}

	public Double getGap() {
		return gap;
	}

	public void setGap(Double gap) {
		this.gap = gap;
	}

	public Double getRelativeVolume() {
		return relativeVolume;
	}

	public void setRelativeVolume(Double relativeVolume) {
		this.relativeVolume = relativeVolume;
	}

	public Double getVolatilityMonth() {
		return volatilityMonth;
	}

	public void setVolatilityMonth(Double volatilityMonth) {
		this.volatilityMonth = volatilityMonth;
	}

	public Double getMarketCap() {
		return marketCap;
	}

	public void setMarketCap(Double marketCap) {
		this.marketCap = marketCap;
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public Double getGrossMargin() {
		return grossMargin;
	}

	public void setGrossMargin(Double grossMargin) {
		this.grossMargin = grossMargin;
	}

	public Double getShortRatio() {
		return shortRatio;
	}

	public void setShortRatio(Double shortRatio) {
		this.shortRatio = shortRatio;
	}

	public Double getPerformanceHalfYear() {
		return performanceHalfYear;
	}

	public void setPerformanceHalfYear(Double performanceHalfYear) {
		this.performanceHalfYear = performanceHalfYear;
	}

	public Double getRsi14() {
		return rsi14;
	}

	public void setRsi14(Double rsi14) {
		this.rsi14 = rsi14;
	}

	public Double getInsiderOwnership() {
		return insiderOwnership;
	}

	public void setInsiderOwnership(Double insiderOwnership) {
		this.insiderOwnership = insiderOwnership;
	}

	public Double getSimpleMovingAverage20day() {
		return simpleMovingAverage20day;
	}

	public void setSimpleMovingAverage20day(Double simpleMovingAverage20day) {
		this.simpleMovingAverage20day = simpleMovingAverage20day;
	}

	public Double getPerformanceMonth() {
		return performanceMonth;
	}

	public void setPerformanceMonth(Double performanceMonth) {
		this.performanceMonth = performanceMonth;
	}

	public Double getpFreeCashFlow() {
		return pFreeCashFlow;
	}

	public void setpFreeCashFlow(Double pFreeCashFlow) {
		this.pFreeCashFlow = pFreeCashFlow;
	}

	public Double getInstitutionalTransactions() {
		return institutionalTransactions;
	}

	public void setInstitutionalTransactions(Double institutionalTransactions) {
		this.institutionalTransactions = institutionalTransactions;
	}

	public Double getPerformanceYear() {
		return performanceYear;
	}

	public void setPerformanceYear(Double performanceYear) {
		this.performanceYear = performanceYear;
	}

	public Double getLtDebtEquity() {
		return ltDebtEquity;
	}

	public void setLtDebtEquity(Double ltDebtEquity) {
		this.ltDebtEquity = ltDebtEquity;
	}

	public Double getAverageVolume() {
		return averageVolume;
	}

	public void setAverageVolume(Double averageVolume) {
		this.averageVolume = averageVolume;
	}

	public Double getEpsGrowthYear() {
		return epsGrowthYear;
	}

	public void setEpsGrowthYear(Double epsGrowthYear) {
		this.epsGrowthYear = epsGrowthYear;
	}
}
