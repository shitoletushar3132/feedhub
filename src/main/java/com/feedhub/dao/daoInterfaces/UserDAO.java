package com.feedhub.dao.daoInterfaces;

import java.util.List;

import com.feedhub.model.User;

public interface UserDAO {
	boolean addUser(User user);

	int addUserAndReturnId(User user);

	User getUserById(int id);

	User authenticate(String usernameOrEmail, String password);

	List<User> getAllUsers();

	boolean updateUser(User user);

	boolean deleteUser(int id);
}
