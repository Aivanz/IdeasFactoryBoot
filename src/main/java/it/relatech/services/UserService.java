package it.relatech.services;

import java.util.List;

import it.relatech.model.User;

public interface UserService {

	User save(User user);

	User update(User user);

	List<User> getList();

	User getById(int id);

	void delete(int id);

	User getByUsername(String username);

}