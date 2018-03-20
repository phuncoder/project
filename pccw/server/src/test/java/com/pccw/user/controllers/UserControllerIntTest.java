package com.pccw.user.controllers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import com.google.gson.Gson;
import com.pccw.user.UserServerApplication;
import com.pccw.user.entity.CreateUserRequest;
import com.pccw.user.entity.UpdateUserRequest;
import com.pccw.user.entity.User;
import com.pccw.user.interfaces.IUserService;

@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = WebEnvironment.RANDOM_PORT,
		classes = UserServerApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
		locations = "classpath:application.inttest.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerIntTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private IUserService service;

	private User user;

	@Before
	public void setUp() {
		user = new User();
		user.setEmail("john.smith@pccw.global.com");
		user.setName("John Smith");
		user.setPassword("john#smit");
		user.setUsername("johnsmith");
		user.setId("johnsmith");
	}


	@Test
	public void test1listAllNoEntry() throws Exception {
		mvc.perform(get("/users").param("size", "5").param("page", "1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void test1listAllIncorrectEntry() throws Exception {
		mvc.perform(get("/users").param("size", "0").param("page", "1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(400));
	}

	@Test
	public void test2listAllExistingUser() throws Exception {

		CreateUserRequest createUserRequest = new CreateUserRequest.Builder().username("listAllExistingUser").build();

		mvc.perform(post("/users").content(convertToJson(createUserRequest))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("name", is("Richard West")))
				.andExpect(jsonPath("username", is("listAllExistingUser")))
				.andExpect(jsonPath("email", is("richard.west@pccw.global.hk")))
				.andExpect(jsonPath("password", is("#####DSS")));

		Assert.assertTrue(service.getUserbyId("listAllExistingUser") != null);

		mvc.perform(get("/users").param("size", "5")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$[0].name", is("Richard West")))
				.andExpect(jsonPath("$[0].username", is("listAllExistingUser")))
				.andExpect(jsonPath("$[0].email", is("richard.west@pccw.global.hk")))
				.andExpect(jsonPath("$[0].password", is("#####DSS")));

		CreateUserRequest createUserRequest1 = new CreateUserRequest.Builder()
		.username("listAllExistingUser1")
		.name("John West")
		.email("John.west@pccw.global.hk")
		.build();

		mvc.perform(post("/users").content(convertToJson(createUserRequest1))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("name", is("John West")))
				.andExpect(jsonPath("username", is("listAllExistingUser1")))
				.andExpect(jsonPath("email", is("John.west@pccw.global.hk")))
				.andExpect(jsonPath("password", is("#####DSS")));

		CreateUserRequest createUserRequest2 = new CreateUserRequest.Builder()
		.username("listAllExistingUser2")
		.name("Diana West")
		.email("diana.west@pccw.global.hk")
		.build();
		mvc.perform(post("/users").content(convertToJson(createUserRequest2))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("name", is("Diana West")))
				.andExpect(jsonPath("username", is("listAllExistingUser2")))
				.andExpect(jsonPath("email", is("diana.west@pccw.global.hk")))
				.andExpect(jsonPath("password", is("#####DSS")));		 

		Assert.assertTrue(service.getUserbyId("listAllExistingUser1") != null);
		Assert.assertTrue(service.getUserbyId("listAllExistingUser2") != null);

		mvc.perform(get("/users").param("size", "2").param("page","2")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$[0].name", is("Diana West")))
				.andExpect(jsonPath("$[0].username", is("listAllExistingUser2")))
				.andExpect(jsonPath("$[0].email", is("diana.west@pccw.global.hk")))
				.andExpect(jsonPath("$[0].password", is("#####DSS")));

	}

	@Test
	public void updateNonExistingUser() throws Exception {

		UpdateUserRequest request = new UpdateUserRequest.Builder().build();
		mvc.perform(put("/users/{id}", "updateNonExistingUser")
				.content(convertToJson(request))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(404));
	}

	@Test
	public void updateExistingUser() throws Exception {

		CreateUserRequest createUserRequest = new CreateUserRequest.Builder().username("updateExistingUser").build();

		mvc.perform(post("/users").content(convertToJson(createUserRequest))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("name", is("Richard West")))
				.andExpect(jsonPath("username", is("updateExistingUser")))
				.andExpect(jsonPath("email", is("richard.west@pccw.global.hk")))
				.andExpect(jsonPath("password", is("#####DSS")));

		Assert.assertTrue(service.getUserbyId("updateExistingUser") != null);

		UpdateUserRequest request = new UpdateUserRequest.Builder()
		.name("Richard West")
		.email("jack@pccw.global.hk")
		.username("JohnnyWest")
		.password("password").build();

		mvc.perform(put("/users/{id}", "updateExistingUser")
				.contentType(MediaType.APPLICATION_JSON)
				.content(convertToJson(request)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("name", is("Richard West")))
				.andExpect(jsonPath("username", is("JohnnyWest")))
				.andExpect(jsonPath("email", is("jack@pccw.global.hk")))
				.andExpect(jsonPath("password", is("password")));
	}


	@Test
	public void testdeleteNonExistentUser() throws Exception {
		mvc.perform(delete("/users/{id}", "testdeleteNonExistentUser")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(404));
	}

	@Test
	public void testdeleteExistentUser() throws Exception {

		CreateUserRequest createUserRequest = new CreateUserRequest.Builder().username("testdeleteExistentUser").build();

		mvc.perform(post("/users").content(convertToJson(createUserRequest))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("name", is("Richard West")))
				.andExpect(jsonPath("username", is("testdeleteExistentUser")))
				.andExpect(jsonPath("email", is("richard.west@pccw.global.hk")))
				.andExpect(jsonPath("password", is("#####DSS")));

		Assert.assertTrue(service.getUserbyId("testdeleteExistentUser") != null);

		mvc.perform(delete("/users/{id}", "testdeleteExistentUser")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		Assert.assertTrue(service.getUserbyId("testdeleteExistentUser") == null);		 
	}


	@Test
	public void testretrieveNoUser() throws Exception {

		mvc.perform(get("/users/{id}", "testretrieveNoUser")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(404));
	}

	@Test
	public void testretrieveUser() throws Exception {

		CreateUserRequest createUserRequest = new CreateUserRequest.Builder().username("rocky").build();

		mvc.perform(post("/users").content(convertToJson(createUserRequest))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("name", is("Richard West")))
				.andExpect(jsonPath("username", is("rocky")))
				.andExpect(jsonPath("email", is("richard.west@pccw.global.hk")))
				.andExpect(jsonPath("password", is("#####DSS")));

		Assert.assertTrue(service.getUserbyId("rocky") != null);

		mvc.perform(get("/users/{id}", "rocky")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("id", is("rocky")))
				.andExpect(jsonPath("name", is("Richard West")))
				.andExpect(jsonPath("username", is("rocky")))
				.andExpect(jsonPath("email", is("richard.west@pccw.global.hk")))
				.andExpect(jsonPath("password", is("#####DSS")));
	}

	@Test
	public void testCreateUser() throws Exception {

		CreateUserRequest createUserRequest = new CreateUserRequest.Builder().build();

		mvc.perform(post("/users").content(convertToJson(createUserRequest))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("name", is("Richard West")))
				.andExpect(jsonPath("username", is("rickwest")))
				.andExpect(jsonPath("email", is("richard.west@pccw.global.hk")))
				.andExpect(jsonPath("password", is("#####DSS")));

		Assert.assertTrue(service.getUserbyId("rickwest") != null);
	}

	@Test
	public void testCreateUserExistUser() throws Exception {

		CreateUserRequest createUserRequest = new CreateUserRequest.Builder().username("johnny").build();

		mvc.perform(post("/users").content(convertToJson(createUserRequest))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("name", is("Richard West")))
				.andExpect(jsonPath("username", is("johnny")))
				.andExpect(jsonPath("email", is("richard.west@pccw.global.hk")))
				.andExpect(jsonPath("password", is("#####DSS")));

		Assert.assertTrue(service.getUserbyId("johnny") != null);

		CreateUserRequest userRequest = new CreateUserRequest.Builder().name("john West").username("johnny").build();

		mvc.perform(post("/users")
				.content(convertToJson(userRequest))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(409));
	}

	@Test
	public void testCreateUserBadRequest() throws Exception {

		CreateUserRequest createUserRequest = new CreateUserRequest.Builder().username("hello").email("").build();

		mvc.perform(post("/users")
				.content(convertToJson(createUserRequest))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(400));
	}

	private <T> String convertToJson(T object) {
		Gson gson = new Gson();
		return gson.toJson(object); 
	}
}
