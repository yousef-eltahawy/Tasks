package com.google.tasks.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@JsonInclude(Include.NON_NULL)
public class SuccessResponse <T> extends BaseResponse{
	
	private T data;
	
	public SuccessResponse(T data) {
		super(true);
		this.data = data;
	}
	
	public SuccessResponse(String message, Integer code) {
		super(true, message, code);
	}
	
	public SuccessResponse(String message, Integer code, T data) {
		this(message, code);
		this.data = data;
	}
}