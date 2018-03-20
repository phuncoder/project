package com.pccw.api.apitest.factory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.pccw.api.apitest.crud.DeleteOperation;
import com.pccw.api.apitest.crud.GetOperation;
import com.pccw.api.apitest.crud.IHttpOperation;
import com.pccw.api.apitest.crud.PostOperation;
import com.pccw.api.apitest.crud.PutOperation;

public class CRUDOperationFactoryTest {

	private CRUDOperationFactory factory;

	@Before
	public void setup() {
		factory = new CRUDOperationFactory();
	}

	@Test
	public void testGetPost() {

		IHttpOperation operation = factory.getCRUDOperation("POST");
		Assert.assertTrue(operation instanceof PostOperation);
	}
	
	@Test
	public void testGetPostDefault() {

		IHttpOperation operation = factory.getCRUDOperation("POSdssdT");
		Assert.assertTrue(operation instanceof PostOperation);
	}

	@Test
	public void testGetPut() {

		IHttpOperation operation = factory.getCRUDOperation("PUT");
		Assert.assertTrue(operation instanceof PutOperation);
	}

	@Test
	public void testGetDelete() {

		IHttpOperation operation = factory.getCRUDOperation("DELETE");
		Assert.assertTrue(operation instanceof DeleteOperation);
	}

	@Test
	public void testGet() {

		IHttpOperation operation = factory.getCRUDOperation("GET");
		Assert.assertTrue(operation instanceof GetOperation);
	}
	
	
}
