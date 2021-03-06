package it.relatech.controller;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.relatech.model.Comment;
import it.relatech.model.Idea;
import it.relatech.model.User;
import it.relatech.services.IdeaService;
import it.relatech.services.UserService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/idea")
public class IdeaController {

	@Autowired
	private IdeaService idserv;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/getmodel")
	public Idea getmodel() {
		return new Idea();
	}

	@PostMapping
	public ResponseEntity<Idea> save(@RequestBody Idea c, Principal principal) throws Exception {
		try {
			if(principal == null) {
				log.info("Saved");
				return new ResponseEntity<Idea>(idserv.save(c), HttpStatus.CREATED);
			}
			
			if(principal != null && c.getId() == 0) {
				log.info("Accepted");
				c.setAccepted(true);
				c.setDateIdea(Timestamp.from(Instant.now()));
				return new ResponseEntity<>(idserv.save(c), HttpStatus.ACCEPTED);
			}
			
			if (c.getId() != 0 || c.isAccepted())
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			else {
				log.info("Saved");
				return new ResponseEntity<Idea>(idserv.save(c), HttpStatus.CREATED);
			}
		} catch (Exception e) {
			log.error("Idea Controller Exception");
			return new ResponseEntity<Idea>(idserv.save(c), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping
	public ResponseEntity<Idea> update(@RequestBody Idea c) {
		try {
			log.info("Saved");
			return new ResponseEntity<Idea>(idserv.update(c), HttpStatus.CREATED);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<Idea>(idserv.update(c), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/vote/{voto}")
	public ResponseEntity<Idea> vote(@RequestBody Idea c, @PathVariable("voto") int voto) throws Exception {
		try {
			log.info("Saved");
			return new ResponseEntity<Idea>(idserv.vote(c, voto), HttpStatus.CREATED);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<Idea>(idserv.vote(c, voto), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/accepting/{id}")
	public ResponseEntity<Idea> accepting(@PathVariable("id") int id) throws Exception {
		try {
			if (id != 0) {
				log.info("Saved");
				return new ResponseEntity<Idea>(idserv.accept(id), HttpStatus.CREATED);
			} else
				return null;
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<Idea>(idserv.accept(id), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/evaluating")
	public ResponseEntity<List<Idea>> list() {
		try {
			log.info("List");
			return new ResponseEntity<List<Idea>>(idserv.listNotAccepted(), HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<List<Idea>>(idserv.listNotAccepted(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping
	public ResponseEntity<List<Idea>> listAccepted() {
		try {
			log.info("List Accepted");
			return new ResponseEntity<List<Idea>>(idserv.listAccepted(), HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<List<Idea>>(idserv.listAccepted(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Idea> getById(@PathVariable("id") int id) {
		try {
			log.info("getById");
			return new ResponseEntity<Idea>(idserv.getById(id), HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<Idea>(idserv.getById(id), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Idea> delete(@PathVariable("id") int id) {
		try {
			log.info("Deleted");
			idserv.deleteId(id);
			return new ResponseEntity<Idea>(HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<Idea>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("comlist/{id}")
	public ResponseEntity<List<Comment>> listComment(@PathVariable("id") int id) {
		try {
			log.info("List");
			return new ResponseEntity<List<Comment>>(idserv.getListComment(id), HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<List<Comment>>(idserv.getListComment(id), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
