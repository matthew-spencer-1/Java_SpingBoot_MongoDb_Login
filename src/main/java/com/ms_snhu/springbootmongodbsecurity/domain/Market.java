package com.ms_snhu.springbootmongodbsecurity.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Extension of the MongoTemplate Class to stream line configuration with the
 * dbFactory
 * 
 * @author matthewspencer
 *
 */
public class Market extends MongoTemplate {

	@Autowired
	MongoDbFactory mongoDbFactory;

	public Market(MongoDbFactory mongoDbFactory) {
		super(mongoDbFactory);
	}
}
