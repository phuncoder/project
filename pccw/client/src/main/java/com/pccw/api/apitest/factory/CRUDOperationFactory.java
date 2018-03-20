package com.pccw.api.apitest.factory;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pccw.api.apitest.config.AppConfig;
import com.pccw.api.apitest.crud.DeleteOperation;
import com.pccw.api.apitest.crud.GetOperation;
import com.pccw.api.apitest.crud.IHttpOperation;
import com.pccw.api.apitest.crud.PostOperation;
import com.pccw.api.apitest.crud.PutOperation;


public class CRUDOperationFactory {

	public IHttpOperation getCRUDOperation(String crud) {
		
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(AppConfig.class);
		ctx.refresh();

		IHttpOperation operation;
		if (RequestMethod.GET.name().equalsIgnoreCase(crud)) {
			operation = ctx.getBean("getOperation", GetOperation.class);
		} else if (RequestMethod.PUT.name().equalsIgnoreCase(crud)) {
			operation = ctx.getBean("putOperation", PutOperation.class);
		} else if (RequestMethod.DELETE.name().equalsIgnoreCase(crud)) {
			operation = ctx.getBean("deleteOperation", DeleteOperation.class);
		} else {
			operation = ctx.getBean("postOperation", PostOperation.class);
		}
		return operation;
	}
}
