package com.pccw.user.controllers;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.google.gson.Gson;
import com.pccw.user.controller.UserController;
import com.pccw.user.entity.CreateUserRequest;
import com.pccw.user.entity.UpdateUserRequest;
import com.pccw.user.entity.User;
import com.pccw.user.exception.UserCannotFindException;
import com.pccw.user.exception.UserExistException;
import com.pccw.user.interfaces.IUserService;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private IUserService service;

	private User user;

	@Before
	public void setUp() {

		user = new User();
		user.setEmail("john.smith@pccw.global.com");
		user.setName("John Smith");
		user.setPassword("john#smit");
		user.setUsername("johnsmith");
	}

	@Test
	public void testretrieveAllUserNotEmptyList() throws Exception {

		given(service.getUsersList(1,5)).willReturn(Arrays.asList(user));
		mvc.perform(get("/users").param("size", "5").param("page", "1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void testretrieveAllUserEmptyList() throws Exception {

		given(service.getUsersList(1,5)).willReturn(Arrays.asList());
		mvc.perform(get("/users").param("size", "5").param("page", "1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void testretrieveUser() throws Exception {

		String id = "ABCDE";

		given(service.findExistUser(id)).willReturn(user);

		mvc.perform(get("/users/{id}", id)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void testretrieveNotFoundUser() throws Exception {

		String id = "ABCDE";
		given(service.findExistUser(id)).willThrow(UserCannotFindException.class);
		mvc.perform(get("/users/{id}", id)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(404));
	}

	@Test
	public void testretrieveUserException() throws Exception {

		String id = "ABCDE";
		given(service.findExistUser(id)).willThrow(Exception.class);
		mvc.perform(get("/users/{id}", id)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(500));
	}


	@Test
	public void testCreateUserBADRequest() throws Exception {

		CreateUserRequest createUserRequest = new CreateUserRequest.Builder().name("").build();
		mvc.perform(post("/users").content(convertToJson(createUserRequest))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(400));

		createUserRequest = new CreateUserRequest.Builder().email("").build();
		mvc.perform(post("/users").content(convertToJson(createUserRequest))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(400));

		createUserRequest = new CreateUserRequest.Builder().username("").build();
		mvc.perform(post("/users").content(convertToJson(createUserRequest))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(400));

		createUserRequest = new CreateUserRequest.Builder().password("").build();
		mvc.perform(post("/users").content(convertToJson(createUserRequest))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(400));
	}


	@Test
	public void testCreateUserSuccessful() throws Exception {

		CreateUserRequest createUserRequest = new CreateUserRequest.Builder().build();

		given(service.createUser(Matchers.any(User.class))).willReturn(user);
		mvc.perform(post("/users").content(convertToJson(createUserRequest))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}	 

	@Test
	public void testCreateUserAlreadyExist() throws Exception  {

		CreateUserRequest createUserRequest = new CreateUserRequest.Builder().build();

		given(service.createUser(Matchers.any(User.class))).willThrow(UserExistException.class);
		mvc.perform(post("/users").content(convertToJson(createUserRequest))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(409));

	}	

	@Test
	public void testUpdateUserExistSuccess() throws Exception {

		String id = "21212";
		UpdateUserRequest request = new UpdateUserRequest.Builder().build();
		given(service.findExistUser(id)).willReturn(user);
		given(service.updateUser(Matchers.any(User.class), Matchers.any(UpdateUserRequest.class))).willReturn(user);
		mvc.perform(put("/users/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(convertToJson(request)))
				.andExpect(status().isOk());
	}	

	@Test
	public void testUpdateUserDoesntExist() throws Exception {

		UpdateUserRequest request = new UpdateUserRequest.Builder().build();
		String id = "21212";

		given(service.findExistUser(id)).willThrow(UserCannotFindException.class);
		mvc.perform(put("/users/{id}", id)
				.content(convertToJson(request))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(404));
	}	

	@Test
	public void testDeleteUserExist() throws Exception {

		String id = "21212";
		given(service.findExistUser(id)).willReturn(user);
		mvc.perform(delete("/users/{id}", id)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}	

	@Test
	public void testDeleteUserDoesntExist() throws Exception {

		String id = "21212";
		given(service.findExistUser(id)).willThrow(UserCannotFindException.class);
		mvc.perform(delete("/users/{id}", id)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(404));

	}	

	private <T> String convertToJson(T object) {
		Gson gson = new Gson();
		return gson.toJson(object); 
	}
}
