package com.pccw.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pccw.user.entity.CreateUserRequest;
import com.pccw.user.entity.UpdateUserRequest;
import com.pccw.user.entity.User;
import com.pccw.user.exception.BadRequestException;
import com.pccw.user.exception.UserCannotFindException;
import com.pccw.user.exception.UserExistException;
import com.pccw.user.interfaces.IUserService;

import java.util.List;

import javax.validation.Valid;

@RestController
public class UserController {

	@Autowired
	IUserService userService;

	@RequestMapping(value="/users",  method=RequestMethod.GET, 
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> getAllUser(@RequestParam(value="size", defaultValue="100") final int size, 
			@RequestParam(value="page", defaultValue="1") final int page) {
		ResponseEntity<?> response = null;

		try {
			List<User> users = userService.getUsersList(page, size);
			response = ResponseEntity.ok(users);
		} catch (BadRequestException e) {
			response = ResponseEntity.badRequest().body(e.getMessageJSON());
		} catch (Exception e) {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return response;
	}

	@RequestMapping(value="/users/{id}", method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> retrieveUser(@PathVariable(name="id", required=true) final String id) {

		ResponseEntity<?> response = null;
		try {
			User user = userService.findExistUser(id);
			response = ResponseEntity.ok().body(user);
		} catch (UserCannotFindException e) {
			response= ResponseEntity.notFound().build();
		} catch (Exception e) {
			response= ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return response;
	}

	@RequestMapping(value="/users",  method=RequestMethod.POST, 
			consumes=MediaType.APPLICATION_JSON_VALUE, 
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> createUser(@Valid @RequestBody(required=true) final CreateUserRequest userRequest, 
			BindingResult bindingResult ) {
		ResponseEntity<?> response;
		try {
			if (bindingResult.hasErrors()) {
				throw new BadRequestException("Please ensure all mandatory fields are filled out");
			}
			User saveduser = userService.createUser(generateUser(userRequest));
			response = ResponseEntity.ok(saveduser);
		} catch(UserExistException e) {
			response = ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessageJSON());
		} catch(BadRequestException e) {
			response = ResponseEntity.badRequest().body(e.getMessageJSON());
		}
		catch(Exception e) {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		return response;
	}

	@RequestMapping(value="/users/{id}", method=RequestMethod.PUT, 
			consumes=MediaType.APPLICATION_JSON_VALUE, 
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> updateUser (@PathVariable(name="id", required=true ) final String id ,
			@RequestBody(required=true) final UpdateUserRequest userRequest) {

		ResponseEntity<?> response;
		try {
			User user = userService.findExistUser(id);
			User updateUser = userService.updateUser(user, userRequest);
			response = ResponseEntity.ok(updateUser);
		} catch (UserCannotFindException e) {
			response =  ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessageJSON());
		} catch (Exception e) {
			response= ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return response;
	}

	@RequestMapping(value="/users/{id}", method=RequestMethod.DELETE, 
			consumes=MediaType.APPLICATION_JSON_VALUE, 
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> deleteUser(@PathVariable(name="id", required=true ) final String id ) {

		ResponseEntity<?> response;
		try {
			User user = userService.findExistUser(id);
			userService.deleteUser(id);
			response = ResponseEntity.ok(user);
		} catch (UserCannotFindException e) {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessageJSON());
		} catch (Exception e) {
			response= ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return response;
	}

	private User generateUser(final CreateUserRequest userRequest) {
		User user = new User();
		user.setEmail(userRequest.getEmail());
		user.setPassword(userRequest.getPassword());
		user.setUsername(userRequest.getUsername());
		user.setName(userRequest.getName());
		return user;
	}

}


