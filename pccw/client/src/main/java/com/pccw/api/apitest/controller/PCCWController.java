package com.pccw.api.apitest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.pccw.api.apitest.config.AppConfig;
import com.pccw.api.apitest.crud.CRUDContext;
import com.pccw.api.apitest.crud.DeleteOperation;
import com.pccw.api.apitest.crud.GetOperation;
import com.pccw.api.apitest.crud.IHttpOperation;
import com.pccw.api.apitest.crud.PostOperation;
import com.pccw.api.apitest.crud.PutOperation;
import com.pccw.api.apitest.factory.CRUDOperationFactory;
import com.pccw.api.apitest.model.Request;

@Controller
public class PCCWController {
	
	@Autowired
	CRUDContext context;
		
	@RequestMapping(value="/home", method=RequestMethod.GET)
	public ModelAndView HomeGet() {

		ModelAndView mview = new ModelAndView("Home");
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(AppConfig.class);
		ctx.refresh();
		Request request = ctx.getBean("request", Request.class);
		mview.addObject("request", request);
		return mview;
	}

	@RequestMapping(value="/home", method=RequestMethod.POST)
	public ModelAndView HomePost(@ModelAttribute(value = "request") Request request)  {
		
		ModelAndView mview = new ModelAndView("Home");

		CRUDOperationFactory factory = new CRUDOperationFactory();
		context.setOperation(factory.getCRUDOperation(request.getMethod()));
		ResponseEntity<String> response = context.exchange(request);

		if (response.hasBody()) {
			request.setResponsebody(response.getBody().toString());	
		}
		request.setResponsestatus(String.valueOf(response.getStatusCodeValue()));
		mview.addObject("request", request);
		return mview;
	}

}
