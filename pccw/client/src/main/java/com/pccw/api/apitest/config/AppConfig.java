package com.pccw.api.apitest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.client.RestTemplate;

import com.pccw.api.apitest.crud.DeleteOperation;
import com.pccw.api.apitest.crud.GetOperation;
import com.pccw.api.apitest.crud.PostOperation;
import com.pccw.api.apitest.crud.PutOperation;
import com.pccw.api.apitest.handler.RestTemplateErrorHandler;
import com.pccw.api.apitest.model.Request;

@Configuration
public class AppConfig {
	
	@Bean
	@DependsOn("restTemplate")
	public PostOperation postOperation() {
		return new PostOperation();
	}
	
	@Bean 
	@DependsOn("restTemplate")
	public GetOperation getOperation() {
		return new GetOperation();
	}

	@Bean
	@DependsOn("restTemplate")
	public PutOperation putOperation() {
		return new PutOperation();
	}
	
	@Bean 
	@DependsOn("restTemplate")
	public DeleteOperation deleteOperation() {
		return new DeleteOperation();
	}
	
	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new RestTemplateErrorHandler());
		return restTemplate;
	}

	@Bean
	public Request request() {
		return new Request();
	}
	
 
	
}
