package com.pccw.api.apitest.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.pccw.api.apitest.model.Request;

public class DeleteOperation implements IHttpOperation {

	@Autowired
	RestTemplate restTemplate;
	
	@Override
	public ResponseEntity<String> exchange(Request request, HttpEntity<?> requestEntity) {
	
		return restTemplate.exchange(request.getUrl(), HttpMethod.DELETE, requestEntity, String.class);
	}

}
