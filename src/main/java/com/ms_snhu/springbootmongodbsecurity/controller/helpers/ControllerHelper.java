package com.ms_snhu.springbootmongodbsecurity.controller.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms_snhu.springbootmongodbsecurity.domain.Stocks;


/**
 * Controller provides helper methods for controller classes
 *  
 * @author matthewspencer
 *
 */
@Component
public class ControllerHelper {
	
	/**
	 * convertIterableToList converts an Itereable of a Type to a List of the same Type  
	 * 
	 * @param iterableToConvert 
	 * @return
	 */
	public <T> List<T> convertIterableToList(Iterable<T> iterableToConvert){
		return StreamSupport.stream(iterableToConvert.spliterator(), false)
                .collect(Collectors.toList());
	}
	
	/**
	 * convertJSONToStocks converts the inputStream of a JSON formatted file to an 
	 * a list of Stocks objects populated with the date found in the JSON file
	 * 
	 * @param fileContents InputStream read from a JSON file
	 * @return 
	 * @throws IOException
	 */
	public List<Stocks> convertJSONToStocks(InputStream fileContents) throws IOException{
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		TypeReference<List<Stocks>> stocksTypeReference = new TypeReference<List<Stocks>>() {};
		
		List<Stocks> stocks = mapper.readValue(fileContents,stocksTypeReference);
		
		return stocks;
	}

}
