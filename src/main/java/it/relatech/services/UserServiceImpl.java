package it.relatech.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.relatech.repository.UserDao;
import it.relatech.model.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao usdao;

	@Override
	public User add(User user) {
		return usdao.save(user);
	}

	@Override
	public List<User> getList() {
		return (List<User>) usdao.findAll();
	}

	@Override
	public void delete(int id) {
		usdao.delete(id);
	}

	@Override
	public User findUserById(int id) {
		return usdao.findOne(id);
	}

	// TODO: Unirlo al save
	@Override
	public User update(User user) {
		return usdao.save(user);
	}

	@Override
	public User findUserByUsername(String username) {
		return usdao.findByUsername(username);
	}

}