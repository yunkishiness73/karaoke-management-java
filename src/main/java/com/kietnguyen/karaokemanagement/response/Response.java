package com.kietnguyen.karaokemanagement.response;

public class Response<T> {
	private Integer code;
	private boolean success;
	private String message = "";
	private T data;
	
	public Response(Integer code, boolean success, T data) {
		this.code = code;
		this.success = success;
		this.data = data;
	}
	
	public Response(Integer code, boolean success, String message) {
		this.code = code;
		this.success = success;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public T getData() {
		return data;
	}
	
	public void setData(T data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
