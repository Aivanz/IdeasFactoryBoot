package it.relatech.services;

import java.security.Principal;
import java.util.List;

import it.relatech.model.User;

public interface UserService {

	User save(User user) throws Exception;

	User update(User user) throws Exception;

	List<User> getList();

	User getById(int id);

	void delete(int id);

	User getByUsername(String username);

	// Controllo principal
	boolean checkAuth(Principal principal, int id);

}