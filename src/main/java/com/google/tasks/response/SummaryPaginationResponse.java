package com.google.tasks.response;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@JsonInclude(Include.NON_NULL)
public class SummaryPaginationResponse<T> extends BaseResponse{
	
	private Long totalElements;
	private List<T> data;
	private Integer numberOfPages;
	private Integer pageNumber;
	private Integer size;
	
	public SummaryPaginationResponse(List<T> data) {
		super(Boolean.TRUE);
		this.data = data ;
	}

	public SummaryPaginationResponse(
			boolean errorStatus, 
			String errorMsg, 
			Integer errorCode, 
			List<T> data, 
			Long totalElements, 
			int numberOfPages
			){
		super(errorStatus, errorMsg, errorCode);
		this.data = data;
		this.totalElements = totalElements;
		this.numberOfPages = numberOfPages;
	}

	public SummaryPaginationResponse(
			String message, 
			Integer msgCode, 
			List<T> data, 
			Long totalElements, 
			int numberOfPages
			){
		super(message, msgCode);
		this.data = data;
		this.totalElements = totalElements;
		this.numberOfPages = numberOfPages;
	}

	public SummaryPaginationResponse(
			String message, 
			Integer msgCode, 
			List<T> data, 
			Long totalElements, 
			Integer numberOfPages, 
			Integer pageNumber, 
			Integer size
			){
		super(message, msgCode);
		this.totalElements = totalElements;
		this.data = data;
		this.numberOfPages = numberOfPages;
		this.pageNumber = pageNumber;
		this.size = size;
	}
}