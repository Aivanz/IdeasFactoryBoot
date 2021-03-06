package it.relatech.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.relatech.model.User;
import it.relatech.services.UserService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userv;

	@Autowired
	private AuthController authController;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/getmodel")
	public User getmodel() {
		return new User();
	}

	// GET
	@GetMapping
	public ResponseEntity<List<User>> getListUsers() {
		try {
			log.info("List");
			return new ResponseEntity<List<User>>(userv.getList(), HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<List<User>>(userv.getList(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// POST
	@PostMapping
	public ResponseEntity<User> save(@RequestBody User user) throws Exception {
		try {
			log.info("Saved");
			return new ResponseEntity<User>(userv.save(user), HttpStatus.CREATED);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<User>(userv.save(user), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// PUT
	@PutMapping("/{id}")
	public ResponseEntity<User> update(@RequestBody User user, @PathVariable("id") int id, Principal principal)
			throws Exception {
		try {
			if (userv.checkAuth(principal, id)) {
				user.setId(id);
				log.info("Saved");
				return new ResponseEntity<User>(userv.update(user), HttpStatus.CREATED);
			} else
				return new ResponseEntity<User>(HttpStatus.METHOD_NOT_ALLOWED);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<User>(userv.update(user), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
		@PutMapping("/changePassword/{id}")
		public ResponseEntity<User> change(@PathVariable("id") int id, @RequestBody User user, Principal principal) throws Exception {
			User nuovoUser = userv.getById(id);
			String nuovaPassword = user.getPassword();
			nuovoUser.setPassword(nuovaPassword);
			try {
				
				if (userv.checkAuth(principal, id)) {
					log.info("Password changed");
					return new ResponseEntity<User>(userv.changePassword(nuovoUser), HttpStatus.OK);
				} else
					return new ResponseEntity<User>(HttpStatus.METHOD_NOT_ALLOWED);
			} catch (Exception e) {
				log.error(e.getMessage());
				return new ResponseEntity<User>(userv.changePassword(nuovoUser), HttpStatus.INTERNAL_SERVER_ERROR);
			}

		}

	// DELETE
	@DeleteMapping("/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable("id") int id, Principal principal, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			if (userv.checkAuth(principal, id)) {
				log.info("Deleted");
				userv.delete(id);

				// Logout obbligatorio in quanto, una volta cancellato l'user, mantiene attiva
				// la sessione
				authController.logoutPage(request, response);
				return new ResponseEntity<User>(HttpStatus.OK);
			} else
				return new ResponseEntity<User>(HttpStatus.METHOD_NOT_ALLOWED);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
