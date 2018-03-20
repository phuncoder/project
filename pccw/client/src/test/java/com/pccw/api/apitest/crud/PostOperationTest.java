package com.pccw.api.apitest.crud;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.when;

import com.pccw.api.apitest.crud.IHttpOperation;
import com.pccw.api.apitest.crud.PostOperation;
import com.pccw.api.apitest.model.Request;

@RunWith(SpringRunner.class)
public class PostOperationTest {
	
    @TestConfiguration
    static class OperationImplTestContextConfiguration {

    	@Bean
    	@DependsOn("restTemplate")
        public IHttpOperation postOperation() {
            return new PostOperation();
        }
    	
    	@Bean
    	public RestTemplate restTemplate() {
    		RestTemplate restTemplate = new RestTemplate();
    		return restTemplate;
    	}

    }

	@Autowired
	@Qualifier(value="postOperation")
	IHttpOperation operation;
	
	@MockBean
	RestTemplate restTemplate;
	
	@Test
	public void testpostOperation() {

		Request request = new Request();
		HttpEntity<?> requestEntity= HttpEntity.EMPTY;
		ResponseEntity<String> response = ResponseEntity.ok().build();

		when(restTemplate.exchange(request.getUrl(), HttpMethod.POST, requestEntity, String.class)).thenReturn(response);
						
		Assert.assertSame(response, operation.exchange(request,requestEntity));
	}
	
}
