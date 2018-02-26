package it.relatech.services;

import java.security.Principal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.relatech.model.User;
import it.relatech.repository.UserDao;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao usdao;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public User save(User user) throws Exception {
		// Controllo mail
		String mail = user.getMail();
		String pattern = "^([A-Z|a-z|0-9](\\.|_){0,1})+[A-Z|a-z|0-9]\\@([A-Z|a-z|0-9])+((\\.){0,1}[A-Z|a-z|0-9]){2}\\.[a-z]{2,3}$";
		Pattern r = Pattern.compile(pattern);
		Matcher matcher = r.matcher(mail);

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		if (matcher.matches())
			return usdao.save(user);
		else
			throw new Exception("Formato mail non valido");
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

	@Override
	public User update(User user) throws Exception {
		User oldUser = usdao.getById(user.getId());

		if (user.getMail() == null)
			user.setMail(oldUser.getMail());

		if (user.getUsername() == null)
			user.setUsername(oldUser.getUsername());

		if (user.getPassword() == null)
			user.setPassword(oldUser.getPassword());

		return save(user);
	}

	@Override
	public User getByUsername(String username) {
		return usdao.getByUsername(username);
	}

	// Controllo sessione
	@Override
	public boolean checkAuth(Principal principal, int id) {
		String nomePrincipal = principal.getName();
		String nomeUtente = getById(id).getUsername();

		if (nomePrincipal.equalsIgnoreCase(nomeUtente))
			return true;
		else
			return false;
	}

}