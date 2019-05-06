package com.trungho.crm.demo.exception;

import java.time.LocalDateTime;

public class CustomerErrorResponse {

	private int status;
	private String message;
	private LocalDateTime timeStamp;
	
	public CustomerErrorResponse() {
		timeStamp = LocalDateTime.now();
	}

	public CustomerErrorResponse(int status, String message) {
		this();
		this.status = status;
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}
}
