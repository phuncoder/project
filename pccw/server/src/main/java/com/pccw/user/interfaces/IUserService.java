package com.pccw.user.interfaces;

import java.util.List;

import com.pccw.user.entity.UpdateUserRequest;
import com.pccw.user.entity.User;
import com.pccw.user.exception.BadRequestException;
import com.pccw.user.exception.UserCannotFindException;
import com.pccw.user.exception.UserExistException;

public interface IUserService {


	public User getUserbyId(String id);

	public User findExistUser(String id) throws UserCannotFindException;

	public List<User> getUsersList(int page, int maxSize) throws BadRequestException;

	public User createUser(User user) throws UserExistException;

	public User updateUser(User user, UpdateUserRequest request);

	public void deleteUser(String id);

}
