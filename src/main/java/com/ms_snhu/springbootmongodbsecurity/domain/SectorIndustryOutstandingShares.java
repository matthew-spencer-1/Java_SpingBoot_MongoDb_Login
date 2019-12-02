package com.ms_snhu.springbootmongodbsecurity.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Model class for the mongoDB Aggregation query finding the total number of
 * Outstanding shares for each industry in a given Sector
 * 
 * @author matthewspencer
 *
 */
public class SectorIndustryOutstandingShares {

	@Id
	@Field("name")
	/* The industry Name */
	private String _id;

	/* The totalOutstandingShares */
	@Field("total")
	private Double total;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

}
