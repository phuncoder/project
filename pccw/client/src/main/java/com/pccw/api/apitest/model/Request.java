package com.pccw.api.apitest.model;

public class Request {

	private String method;

	private String url;
	
	private String body;
	
	private String responsebody;
	
	private String responsestatus;


	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getResponsebody() {
		return responsebody;
	}

	public void setResponsebody(String responsebody) {
		this.responsebody = responsebody;
	}

	public String getResponsestatus() {
		return responsestatus;
	}

	public void setResponsestatus(String responsestatus) {
		this.responsestatus = responsestatus;
	}
	
}
