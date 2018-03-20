package com.pccw.user.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pccw.user.entity.UpdateUserRequest;
import com.pccw.user.entity.User;
import com.pccw.user.exception.BadRequestException;
import com.pccw.user.exception.UserCannotFindException;
import com.pccw.user.exception.UserExistException;
import com.pccw.user.interfaces.IUserService;
import com.pccw.user.interfaces.UserRepository;

@Service 
public class UserServiceImpl implements IUserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public User getUserbyId(String id) {

		return userRepository.findOne(id);
	}

	@Override
	public User findExistUser(String id) throws UserCannotFindException {

		User user = userRepository.findOne(id);
		if (user == null) {
			throw new UserCannotFindException("Can not find user with id of " + id);
		}
		return user;
	}

	@Override
	public List<User> getUsersList (int page, int pagesize) throws BadRequestException  {

		if ( page <= 0 || pagesize <= 0) {
			throw new BadRequestException("Please ensure pagesize or page number are positive");
		}
		int minRange = (page-1)*pagesize;
		int maxRange = minRange + pagesize;

		List<User> splice = new ArrayList<>();
		List<User> users = userRepository.findAll().stream().limit(maxRange).collect(Collectors.toList());

		for( int i = minRange ; i < maxRange && i <users.size(); i++) {
			splice.add(users.get(i));
		}
		return splice;
	}

	@Override
	public User createUser(User user) throws UserExistException {

		if (userRepository.findOne(user.getUsername()) != null) {
			throw new UserExistException(user.getUsername() + "Already Exist");
		}
		user.setId(user.getUsername());
		return userRepository.save(user);
	}

	@Override
	public User updateUser(User user, UpdateUserRequest request) {

		if (request.getUsername() != null && (!request.getUsername().equals(user.getUsername()))) {
			user.setUsername(request.getUsername());
		}
		if (request.getName() != null && (!request.getName().equals(user.getName()))) {
			user.setName(request.getName());
		}
		if (request.getEmail() != null && (!request.getEmail().equals(user.getEmail()))) {
			user.setEmail(request.getEmail());
		}
		if (request.getPassword() != null && (!request.getPassword().equals(user.getPassword()))) {
			user.setPassword(request.getPassword());
		}
		return userRepository.save(user);
	}

	@Override
	public void deleteUser(String id) {

		userRepository.delete(id);

	}

}
