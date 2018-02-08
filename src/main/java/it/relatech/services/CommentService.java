package it.relatech.services;

import java.util.List;

import it.relatech.model.Comment;

public interface CommentService {

	Comment save(Comment comment);

	Comment update(Comment comment);

	Comment getId(int id);

	void deleteId(int id);

	List<Comment> list();
}
