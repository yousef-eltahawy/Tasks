package com.google.tasks.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
public abstract class BaseResponse {
	
	@JsonIgnore
	private Boolean success;
	private String message;
	private Integer msgCode;
	
	public BaseResponse(Boolean success) {
		this();
		this.success = success;
	}

	public BaseResponse(String message, Integer msgCode) {
		this(false);
		this.message = message;
		this.msgCode = msgCode;
	}

	public BaseResponse(Boolean success, String message, Integer msgCode) {
		this(message, msgCode);
		this.success = success;
	}
	
}