package com.pccw.api.apitest.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.google.gson.Gson;
import com.pccw.api.apitest.config.AppConfig;
import com.pccw.api.apitest.crud.CRUDContext;
import com.pccw.api.apitest.model.Request;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest(PCCWController.class)
public class PCCWControllerTest {

	@MockBean
	private CRUDContext context;

	@Autowired
	private MockMvc mvc;

	@Test
	public void testHomeGet() throws Exception {

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(AppConfig.class);
		ctx.refresh();

		Request request = ctx.getBean("request", Request.class);

		ModelAndView view = mvc.perform(get("/home").contentType(MediaType.APPLICATION_JSON))
				.andReturn().getModelAndView();

		Assert.assertEquals("Home", view.getViewName());
		Request actual = (Request) view.getModel().get("request");
		Assert.assertSame(request.getBody(), actual.getBody());
		Assert.assertSame(request.getMethod(), actual.getMethod());
		Assert.assertSame(request.getResponsebody(), actual.getResponsebody());
		Assert.assertSame(request.getResponsestatus(), actual.getResponsestatus());

	}	 

	@Test
	public void testHomePostPost() throws Exception {

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(AppConfig.class);
		ctx.refresh();

		Request request = ctx.getBean("request", Request.class);
		request.setMethod("POST");

		ResponseEntity<String> response = ResponseEntity.ok("This is the body");
		
		given(context.exchange(Mockito.any(Request.class))).willReturn(response);
		
		ModelAndView view = mvc.perform(post("/home").contentType(MediaType.APPLICATION_JSON).content(convertToJson(request)))
				.andReturn().getModelAndView();

		Assert.assertEquals("Home", view.getViewName());
		Request actual = (Request) view.getModel().get("request");
		Assert.assertSame(response.getBody().toString(), actual.getResponsebody());
		Assert.assertTrue(response.getStatusCodeValue() == Integer.parseInt(actual.getResponsestatus()));
	}	 


	@Test
	public void testHomePostDelete() throws Exception {

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(AppConfig.class);
		ctx.refresh();

		Request request = ctx.getBean("request", Request.class);
		request.setMethod("DELETE");

		ResponseEntity<String> response = ResponseEntity.ok("This is the body");
		
		given(context.exchange(Mockito.any(Request.class))).willReturn(response);
		
		ModelAndView view = mvc.perform(post("/home").contentType(MediaType.APPLICATION_JSON).content(convertToJson(request)))
				.andReturn().getModelAndView();

		Assert.assertEquals("Home", view.getViewName());
		Request actual = (Request) view.getModel().get("request");
		Assert.assertSame(response.getBody().toString(), actual.getResponsebody());
		Assert.assertTrue(response.getStatusCodeValue() == Integer.parseInt(actual.getResponsestatus()));
	}	 

	@Test
	public void testHomePostPut() throws Exception {

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(AppConfig.class);
		ctx.refresh();

		Request request = ctx.getBean("request", Request.class);
		request.setMethod("PUT");

		ResponseEntity<String> response = ResponseEntity.ok("This is the body");
		
		given(context.exchange(Mockito.any(Request.class))).willReturn(response);
		
		ModelAndView view = mvc.perform(post("/home").contentType(MediaType.APPLICATION_JSON).content(convertToJson(request)))
				.andReturn().getModelAndView();

		Assert.assertEquals("Home", view.getViewName());
		Request actual = (Request) view.getModel().get("request");
		Assert.assertSame(response.getBody().toString(), actual.getResponsebody());
		Assert.assertTrue(response.getStatusCodeValue() == Integer.parseInt(actual.getResponsestatus()));
	}	 
	
	@Test
	public void testHomePostGet() throws Exception {

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(AppConfig.class);
		ctx.refresh();

		Request request = ctx.getBean("request", Request.class);
		request.setMethod("GET");

		ResponseEntity<String> response = ResponseEntity.ok("This is the body");
		
		given(context.exchange(Mockito.any(Request.class))).willReturn(response);
		
		ModelAndView view = mvc.perform(post("/home").contentType(MediaType.APPLICATION_JSON).content(convertToJson(request)))
				.andReturn().getModelAndView();

		Assert.assertEquals("Home", view.getViewName());
		Request actual = (Request) view.getModel().get("request");
		Assert.assertSame(response.getBody().toString(), actual.getResponsebody());
		Assert.assertTrue(response.getStatusCodeValue() == Integer.parseInt(actual.getResponsestatus()));
	}	 



	private <T> String convertToJson(T object) {
		Gson gson = new Gson();
		return gson.toJson(object); 
	}

}
