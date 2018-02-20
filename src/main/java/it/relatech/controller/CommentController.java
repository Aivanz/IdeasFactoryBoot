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

import it.relatech.model.Comment;
import it.relatech.services.CommentService;
import it.relatech.services.IdeaService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentService comserv;
	@Autowired
	private IdeaService idserv;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/getmodel")
	public Comment getmodel() {
		return new Comment();
	}

	@PostMapping
	public ResponseEntity<Comment> save(@RequestBody Comment c) throws Exception {
		try {
			log.info("Saved");
			return new ResponseEntity<Comment>(comserv.save(c), HttpStatus.CREATED);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<Comment>(comserv.save(c), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/{id}")
	public ResponseEntity<Comment> saveLink(@PathVariable("id") int id, @RequestBody Comment c) throws Exception {
		try {
			c.setIdea(idserv.getById(id));
			log.info("Saved");
			return new ResponseEntity<Comment>(comserv.save(c), HttpStatus.CREATED);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<Comment>(comserv.save(c), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping
	public ResponseEntity<Comment> update(@RequestBody Comment c) throws Exception {
		try {
			log.info("Saved");
			return new ResponseEntity<Comment>(comserv.update(c), HttpStatus.CREATED);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<Comment>(comserv.update(c), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/accepting/{id}")
	public ResponseEntity<Comment> accepted(@PathVariable("id") int id) throws Exception {
		try {
			log.info("Saved");
			return new ResponseEntity<Comment>(comserv.accept(id), HttpStatus.CREATED);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<Comment>(comserv.accept(id), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping
	public ResponseEntity<List<Comment>> listEvaluating() {
		try {
			log.info("List");
			return new ResponseEntity<List<Comment>>(comserv.listEvaluating(), HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<List<Comment>>(comserv.listEvaluating(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/listAll")
	public ResponseEntity<List<Comment>> list() {
		try {
			log.info("List");
			return new ResponseEntity<List<Comment>>(comserv.list(), HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<List<Comment>>(comserv.list(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Comment> deleteComment(@PathVariable("id") int id) {
		try {
			log.info("Deleted");
			comserv.deleteId(id);
			return new ResponseEntity<Comment>(HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<Comment>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
