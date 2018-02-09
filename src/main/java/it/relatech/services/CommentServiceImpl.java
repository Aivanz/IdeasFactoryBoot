package it.relatech.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.relatech.repository.CommentDao;
import it.relatech.model.Comment;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentDao cdao;

	@Override
	public Comment save(Comment comment) {
		comment.setDateComment(Timestamp.from(Instant.now()));
		return cdao.save(comment);
	}

	@Override
	public Comment getId(int id) {
		return cdao.findOne(id);
	}

	@Override
	public void deleteId(int id) {
		cdao.delete(id);
	}

	@Override
	public List<Comment> list() {
		return (List<Comment>) cdao.findAll();
	}

	@Override
	public List<Comment> listEvaluating() {
		return cdao.getCommentByAccepted(true);
	}

	@Override
	public Comment accept(Comment comment) {
		Comment com = cdao.getCommentById(comment.getId());
		com.setAccepted(true);
		return update(com);
	}

	// TODO: Unire al save
	@Override
	public Comment update(Comment comment) {
		return cdao.save(comment);
	}

}
