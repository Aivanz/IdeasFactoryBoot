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
	public User save(User user) {
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
	public User getById(int id) {
		return usdao.findOne(id);
	}

	// TODO: Unirlo al save
	@Override
	public User update(User user) {
		return usdao.save(user);
	}

	@Override
	public User getByUsername(String username) {
		return usdao.getByUsername(username);
	}

}