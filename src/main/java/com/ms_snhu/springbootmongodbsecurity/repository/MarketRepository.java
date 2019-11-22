package com.ms_snhu.springbootmongodbsecurity.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.ms_snhu.springbootmongodbsecurity.domain.Stocks;

/**
 * MarketRepository is an extension of MongoRepository enabling custom
 * functionality as needed for the Market db and Stocks collection
 * 
 * @author matthewspencer
 *
 */
public interface MarketRepository extends MongoRepository<Stocks, String> {

	Stocks findBy_id(ObjectId _id);

	Stocks findByTicker(String ticker);

	List<Stocks> findByCompanyName(String companyName);

	List<Stocks> findTop5ByIndustryInOrderByPerformanceYTDDesc(String industryName);
}
