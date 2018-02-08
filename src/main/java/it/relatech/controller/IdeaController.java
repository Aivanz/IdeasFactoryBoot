package it.relatech.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import it.relatech.services.IdeaService;

//@CrossOrigin (origins = "http://localhost:4200")
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
	public ResponseEntity<Idea> saveUpdate(@RequestBody Idea c) {
		try {
			log.info("Saved");
			return new ResponseEntity<Idea>(idserv.save(c), HttpStatus.CREATED);
		} catch (Exception e) {
			log.error(e.getMessage());
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

	@PutMapping("/accepting")
	public ResponseEntity<Idea> accepted(@RequestBody Idea idea) {
		try {
			idea.setAccepted(true);
			log.info("Saved");
			return new ResponseEntity<Idea>(idserv.update(idea), HttpStatus.CREATED);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<Idea>(idserv.update(idea), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/evaluating")
	public ResponseEntity<List<Idea>> list() {
		try {
			log.info("List");
			return new ResponseEntity<List<Idea>>(idserv.list(), HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<List<Idea>>(idserv.list(), HttpStatus.INTERNAL_SERVER_ERROR);
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
	public ResponseEntity<Idea> getIdea(@PathVariable("id") int id) {
		try {
			log.info("List");
			return new ResponseEntity<Idea>(idserv.getId(id), HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<Idea>(idserv.getId(id), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Idea> deleteIdea(@PathVariable("id") int id) {
		try {
			log.info("Deleted");
			return new ResponseEntity<Idea>(HttpStatus.OK);
			// return new ResponseEntity<Idea>(idserv.deleteId(id), HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<Idea>(HttpStatus.INTERNAL_SERVER_ERROR);
			// return new ResponseEntity<Idea>(idserv.deleteId(id),
			// HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("comlist/{id}")
	public ResponseEntity<List<Comment>> listcomment(@PathVariable("id") int id) {
		List<Comment> cl = null;
		try {
			cl = idserv.getId(id).getComlist();
			log.info("List");
			return new ResponseEntity<List<Comment>>(cl, HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<List<Comment>>(cl, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
