package it.relatech.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.relatech.model.User;

@Repository
public interface UserDao extends CrudRepository<User, Integer> {

	User findByUsername(String username);
}
