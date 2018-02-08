package it.relatech.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.relatech.model.Comment;

@Repository
public interface CommentDao extends CrudRepository<Comment, Integer> {

	Comment getCommentById(int id);

}
