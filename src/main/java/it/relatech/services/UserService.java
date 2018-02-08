package it.relatech.services;

import java.util.List;

import it.relatech.model.User;

public interface UserService {

	User add(User user);

	User update(User user);

	List<User> getList();

	User findUserById(int id);

	void delete(int id);

	User findUserByUsername(String username);

}