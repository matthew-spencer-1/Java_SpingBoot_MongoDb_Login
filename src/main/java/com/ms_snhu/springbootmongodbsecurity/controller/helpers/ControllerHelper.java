package com.ms_snhu.springbootmongodbsecurity.controller.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms_snhu.springbootmongodbsecurity.domain.Stocks;
import com.ms_snhu.springbootmongodbsecurity.domain.User;
import com.ms_snhu.springbootmongodbsecurity.repository.RoleRepository;
import com.ms_snhu.springbootmongodbsecurity.service.CustomUserDetailsService;

/**
 * Controller provides helper methods for controller classes
 * 
 * @author matthewspencer
 *
 */
@Component
public class ControllerHelper {

  @Autowired
  private CustomUserDetailsService userService;
  @Autowired
  private RoleRepository           roleRpository;

  /**
   * convertIterableToList converts an Itereable of a Type to a List of the same
   * Type
   * 
   * @param iterableToConvert
   * @return
   */
  public <T> List<T> convertIterableToList(Iterable<T> iterableToConvert) {
    return StreamSupport.stream(iterableToConvert.spliterator(), false).collect(Collectors.toList());
  }

  /**
   * convertJSONToStocks converts the inputStream of a JSON formatted file to an a
   * list of Stocks objects populated with the date found in the JSON file
   * 
   * @param fileContents InputStream read from a JSON file
   * @return
   * @throws IOException
   */
  public List<Stocks> convertJSONToStocks(InputStream fileContents) throws IOException {

    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    TypeReference<List<Stocks>> stocksTypeReference = new TypeReference<List<Stocks>>() {};

    List<Stocks> stocks = mapper.readValue(fileContents, stocksTypeReference);

    return stocks;
  }

  public List<Stocks> convertCSVToStocks(InputStream fileContent) throws IOException, ParseException {
    List<Stocks> stockRecords = new ArrayList<Stocks>();

    CSVParser parser = CSVParser.parse(fileContent, StandardCharsets.ISO_8859_1, CSVFormat.DEFAULT.withQuote(null));
    for (CSVRecord csvRecord : parser) {
      Stocks stock = this.stockFromCsvRecord(csvRecord);
      stockRecords.add(stock);
    }

    return stockRecords;
  }

  /**
   * helper method to get and return the current authenticated user
   * 
   * @return Authenticated User
   */
  public User getAuthenticatedUser() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User user = userService.findUserByEmail(auth.getName());

    return user;
  }

  public Boolean isUserAdmin(User user) {
    return user.getRoles().contains(roleRpository.findByRole("ADMIN"));
  }

  /**
   * Trigger redirect from RESTful service to http web service error page
   * 
   * @param httpServletResponse
   * @param messageText
   */
  public void redirectToError(HttpServletResponse httpServletResponse, String messageText) {
    httpServletResponse.setHeader("Location", "/error?message=" + messageText);
    httpServletResponse.setStatus(302);
  }

  /**
   * Trigger redirect from RESTful service to http web service dashboard
   * 
   * @param httpServletResponse
   */
  public void redirectToDashboard(HttpServletResponse httpServletResponse) {
    httpServletResponse.setHeader("Location", "/dashboard");
    httpServletResponse.setStatus(302);
  }

  /**
   * Convert the data contained in a csvRecord object into a Stocks object
   * 
   * @param csvRecord
   * @return
   * @throws ParseException
   */
  private Stocks stockFromCsvRecord(CSVRecord csvRecord) throws ParseException {

    Stocks stock = new Stocks();
    stock.set_id(ObjectId.get().toString());
    stock.setTicker(csvRecord.get(1));
    stock.setCompanyName(csvRecord.get(2));
    stock.setSector(csvRecord.get(3));
    stock.setIndustry(csvRecord.get(4));
    stock.setCountry(csvRecord.get(5));
    stock.setProfitMargin(this.parseDoubleNullSafe(csvRecord.get(6)));
    stock.setInstitutionalOwnership(this.parseDoubleNullSafe(csvRecord.get(7)));
    stock.setEpsGrowth5years(this.parseDoubleNullSafe(csvRecord.get(8)));
    stock.setTotalDebtEquity(this.parseDoubleNullSafe(csvRecord.get(9)));
    stock.setCurrentRatio(this.parseDoubleNullSafe(csvRecord.get(10)));
    stock.setReturnOnAssets(this.parseDoubleNullSafe(csvRecord.get(11)));
    stock.setPs(this.parseDoubleNullSafe(csvRecord.get(12)));
    stock.setChangeFromOpen(this.parseDoubleNullSafe(csvRecord.get(13)));
    stock.setPerformanceYTD(this.parseDoubleNullSafe(csvRecord.get(14)));
    stock.setPerformanceWeek(this.parseDoubleNullSafe(csvRecord.get(15)));
    stock.setQuickRatio(this.parseDoubleNullSafe(csvRecord.get(16)));
    stock.setInsiderTransactions(this.parseDoubleNullSafe(csvRecord.get(17)));
    stock.setPb(this.parseDoubleNullSafe(csvRecord.get(18)));
    stock.setEpsGrowthQuarterOver(this.parseDoubleNullSafe(csvRecord.get(19)));
    stock.setPerformanceQuarter(this.parseDoubleNullSafe(csvRecord.get(20)));
    stock.setForwardPe(this.parseDoubleNullSafe(csvRecord.get(21)));
    stock.setPe(this.parseDoubleNullSafe(csvRecord.get(22)));
    stock.setSimpleMovingAverage200Day(this.parseDoubleNullSafe(csvRecord.get(23)));
    stock.setSharesOutstanding(this.parseDoubleNullSafe(csvRecord.get(24)));
    stock.setEarningsDate(this.parseDateNullSafe(csvRecord.get(25)));
    stock.setHigh52Week(this.parseDoubleNullSafe(csvRecord.get(26)));
    stock.setpCash(this.parseDoubleNullSafe(csvRecord.get(27)));
    stock.setChange(this.parseDoubleNullSafe(csvRecord.get(28)));
    stock.setAnalyistRecom(this.parseDoubleNullSafe(csvRecord.get(29)));
    stock.setVolatilityMonth(this.parseDoubleNullSafe(csvRecord.get(30)));
    stock.setReturnOnEquity(this.parseDoubleNullSafe(csvRecord.get(31)));
    stock.setLow50Day(this.parseDoubleNullSafe(csvRecord.get(32)));
    stock.setPrice(this.parseDoubleNullSafe(csvRecord.get(33)));
    stock.setHigh50Day(this.parseDoubleNullSafe(csvRecord.get(34)));
    stock.setReturnOnInvestment(this.parseDoubleNullSafe(csvRecord.get(35)));
    stock.setSharesFloat(this.parseDoubleNullSafe(csvRecord.get(36)));
    stock.setDividendYield(this.parseDoubleNullSafe(csvRecord.get(37)));
    stock.setEpsGrowth5years(this.parseDoubleNullSafe(csvRecord.get(38)));
    stock.setBeta(this.parseDoubleNullSafe(csvRecord.get(39)));
    stock.setSalesGrowthQuarterOver(this.parseDoubleNullSafe(csvRecord.get(40)));
    stock.setOperatingMargin(this.parseDoubleNullSafe(csvRecord.get(41)));
    stock.setEpsTtm(this.parseDoubleNullSafe(csvRecord.get(42)));
    stock.setPeg(this.parseDoubleNullSafe(csvRecord.get(43)));
    stock.setFloatShort(this.parseDoubleNullSafe(csvRecord.get(43)));
    stock.setLow52week(this.parseDoubleNullSafe(csvRecord.get(44)));
    stock.setAverageTrueRange(this.parseDoubleNullSafe(csvRecord.get(45)));
    stock.setSalesGrowthPastFiveYears(this.parseDoubleNullSafe(csvRecord.get(46)));
    stock.setGap(this.parseDoubleNullSafe(csvRecord.get(47)));
    stock.setRelativeVolume(this.parseDoubleNullSafe(csvRecord.get(48)));
    stock.setVolatilityMonth(this.parseDoubleNullSafe(csvRecord.get(49)));
    stock.setMarketCap(this.parseDoubleNullSafe(csvRecord.get(50)));
    stock.setVolume(this.parseDoubleNullSafe(csvRecord.get(51)));
    stock.setGrossMargin(this.parseDoubleNullSafe(csvRecord.get(52)));
    stock.setShortRatio(this.parseDoubleNullSafe(csvRecord.get(53)));
    stock.setPerformanceHalfYear(this.parseDoubleNullSafe(csvRecord.get(54)));
    stock.setRsi14(this.parseDoubleNullSafe(csvRecord.get(55)));
    stock.setInsiderOwnership(this.parseDoubleNullSafe(csvRecord.get(56)));
    stock.setSimpleMovingAverage20day(this.parseDoubleNullSafe(csvRecord.get(57)));
    stock.setPerformanceMonth(this.parseDoubleNullSafe(csvRecord.get(58)));
    stock.setpFreeCashFlow(this.parseDoubleNullSafe(csvRecord.get(59)));
    stock.setInstitutionalTransactions(this.parseDoubleNullSafe(csvRecord.get(60)));
    stock.setPerformanceYear(this.parseDoubleNullSafe(csvRecord.get(61)));
    stock.setLtDebtEquity(this.parseDoubleNullSafe(csvRecord.get(62)));
    stock.setAverageVolume(this.parseDoubleNullSafe(csvRecord.get(63)));
    stock.setEpsGrowthYear(this.parseDoubleNullSafe(csvRecord.get(64)));
    stock.setSimpleMovingAverage50day(this.parseDoubleNullSafe(csvRecord.get(65)));

    return stock;
  }

  private Double parseDoubleNullSafe(String stringToParse) {
    return StringUtils.isEmpty(stringToParse) ? null : Double.parseDouble(stringToParse);
  }

  private Date parseDateNullSafe(String stringToParse) throws ParseException {
    String formatString = "yyyy-mm-dd'T'hh:mm:ss.S'Z'";
    SimpleDateFormat sdf = new SimpleDateFormat(formatString);

    return StringUtils.isEmpty(stringToParse) ? null : sdf.parse(stringToParse);

  }

}
