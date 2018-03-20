package com.pccw.user.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pccw.user.entity.User;

public interface UserRepository extends JpaRepository<User, String>{

	@Query("SELECT u FROM User u WHERE LOWER(u.username) = LOWER(:username)")
	public List<User> findByUserName(@Param("username") String username);
}
