package com.pccw.api.apitest.crud;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.pccw.api.apitest.crud.CRUDContext;
import com.pccw.api.apitest.crud.IHttpOperation;
import com.pccw.api.apitest.model.Request;

import static org.mockito.Mockito.when;

import org.junit.Assert;

@RunWith(SpringRunner.class)
public class CRUDContextTest {
    
	@MockBean
	private IHttpOperation operation;
	
	@Test
	public void exchangeTest(){
		
		Request request = new Request();
		ResponseEntity<String> response = ResponseEntity.ok().build();
		when(operation.exchange(Mockito.any(Request.class), Mockito.any(HttpEntity.class))).thenReturn(response);
		CRUDContext context = new CRUDContext();
		context.setOperation(operation);
		Assert.assertSame(response, context.exchange(request));
		
	}
	
}
