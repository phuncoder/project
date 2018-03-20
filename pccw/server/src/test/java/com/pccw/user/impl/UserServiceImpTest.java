package com.pccw.user.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.pccw.user.entity.UpdateUserRequest;
import com.pccw.user.entity.User;
import com.pccw.user.exception.UserCannotFindException;
import com.pccw.user.exception.UserExistException;
import com.pccw.user.impl.UserServiceImpl;
import com.pccw.user.interfaces.IUserService;
import com.pccw.user.interfaces.UserRepository;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UserServiceImpTest {

	@TestConfiguration
	static class UserServiceImplTestContextConfiguration {

		@Bean
		public IUserService userService() {
			return new UserServiceImpl();
		}
	}

	@Autowired
	private IUserService userService;

	@MockBean
	private UserRepository userRepository;

	private User user;

	@Before
	public void setUp() {

		user = new User();
		user.setEmail("john.smith@pccw.global.com");
		user.setName("John Smith");
		user.setPassword("john#smit");
		user.setUsername("johnsmith");
		user.setId("12345");
	}

	@Test
	public void testGetuserbyId() {

		String id = new String("2121");

		when(userRepository.findOne(id)).thenReturn(user);
		User returnUser = userService.getUserbyId(id);

		Assert.assertEquals(user, returnUser);
		verify(userRepository,times(1)).findOne(id); 
	}

	@Test( expected= UserCannotFindException.class)
	public void testfindExistUserNotExist() throws UserCannotFindException {

		String id = "212";
		when(userRepository.findOne(id)).thenReturn(null);
		userService.findExistUser(id);
	}

	@Test
	public void testfindExistUser() throws UserCannotFindException {

		String id = "212";
		when(userRepository.findOne(id)).thenReturn(user);
		User findUser = userService.findExistUser(id);
		Assert.assertSame(findUser, user);
	}

	@Test( expected= UserExistException.class) 
	public void testCreateUserExist() throws UserExistException {

		when(userRepository.findOne(Matchers.anyString())).thenReturn(new User());

		userService.createUser(user);

		verify(userRepository, never()).save(user);
	}

	@Test
	public void testCreateUser() throws UserExistException {

		when(userRepository.save(user)).thenReturn(user);

		Assert.assertEquals(user, userService.createUser(user));
		verify(userRepository,times(1)).save(user);
	}


	@Test
	public void testUpdateuser() {

		UpdateUserRequest request = new UpdateUserRequest.Builder().build();
		when(userRepository.save(user)).thenReturn(user);
		userService.updateUser(user, request);
		Assert.assertSame(request.getEmail(),user.getEmail());
		Assert.assertSame(request.getUsername(),user.getUsername());
		Assert.assertSame(request.getPassword(),user.getPassword());
		Assert.assertSame(request.getName(), user.getName());
		verify(userRepository,times(1)).save(user);
	}


	@Test
	public void testdeleteUser() {

		String id = "ABCDE";
		doNothing().when(userRepository).delete(id);
		userService.deleteUser(id);
		verify(userRepository,times(1)).delete(id);
	}

}
