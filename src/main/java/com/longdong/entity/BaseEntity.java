package com.longdong.entity;

import java.io.Serializable;
import java.sql.Timestamp;



public class BaseEntity implements Serializable{
	private Integer rows = 10;           //一页显示多少条
	private Integer page = 1;			//当前页
	private Integer skipResults=0;
	private Integer maxResult=10;
	
	
	private String order="desc";			//按升序，降序排序    ,desc/asc
	private String sort="id";			 //排序字段名称
	
	private Timestamp createdDate;
	private Integer createdId;
	private Timestamp updatedDate;
	private Integer updateId;
	
	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}


	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}

	public Integer getSkipResults() {
		return (this.page-1) * this.rows;
	}

	public void setSkipResults(Integer skipResults) {
		this.skipResults = skipResults;
	}

	public Integer getMaxResult() {
		return this.page * this.rows;
	}

	public void setMaxResult(Integer maxResult) {
		this.maxResult = maxResult;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getCreatedId() {
		return createdId;
	}

	public void setCreatedId(Integer createdId) {
		this.createdId = createdId;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Integer getUpdateId() {
		return updateId;
	}

	public void setUpdateId(Integer updateId) {
		this.updateId = updateId;
	}
	


	
}
