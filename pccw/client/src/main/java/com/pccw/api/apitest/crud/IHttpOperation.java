package com.pccw.api.apitest.crud;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import com.pccw.api.apitest.model.Request;

public interface IHttpOperation {
	
	public ResponseEntity<String> exchange(Request request, HttpEntity<?> requestEntity);

}
