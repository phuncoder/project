package com.pccw.api.apitest.crud;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.pccw.api.apitest.model.Request;

@Component
public class CRUDContext {

	private IHttpOperation operation;

	public void setOperation(IHttpOperation operation) {
		this.operation = operation;
	}

	public ResponseEntity<String> exchange(Request request){

		HttpHeaders requestHeaders=new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<?> requestEntity=new HttpEntity(request.getBody(),requestHeaders);

		return operation.exchange(request, requestEntity);

	}
}