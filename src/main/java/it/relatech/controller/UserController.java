package it.relatech.controller;

import java.util.List;

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
	public ResponseEntity<User> saveUpdate(@RequestBody User user) {
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
	public ResponseEntity<User> update(@RequestBody User user, @PathVariable("id") int id) {
		try {
			user.setId(id);
			log.info("Saved");
			return new ResponseEntity<User>(userv.update(user), HttpStatus.CREATED);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<User>(userv.update(user), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// DELETE
	@DeleteMapping("/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable("id") int id) {
		try {
			log.info("Deleted");
			userv.delete(id);
			return new ResponseEntity<User>(HttpStatus.OK);
			// return new ResponseEntity<User>(userv.delete(id), HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
			// return new ResponseEntity<User>(userv.delete(id),
			// HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
