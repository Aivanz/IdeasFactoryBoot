package it.relatech.services;

import java.util.List;

import it.relatech.model.Comment;

public interface CommentService {

	Comment save(Comment comment) throws Exception;

	Comment update(Comment comment) throws Exception;

	Comment getById(int id);

	void deleteId(int id);

	List<Comment> list();

	Comment accept(int id) throws Exception;

	List<Comment> listEvaluating();

}
