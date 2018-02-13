package it.relatech.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.relatech.repository.CommentDao;
import it.relatech.mail.EmailConfig;
import it.relatech.mail.MailObject;
import it.relatech.model.Comment;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentDao cdao;

	@Autowired
	private EmailConfig emailService;

	public void sendMail(String destinatario, String oggetto, String testo) {
		MailObject mailObject = new MailObject();
		mailObject.setTo(destinatario);
		mailObject.setSubject(oggetto);
		mailObject.setText(testo);
		emailService.sendSimpleMessage(mailObject);
	}

	@Override
	public Comment save(Comment comment) {
		Comment temp = new Comment();
		comment.setDateComment(Timestamp.from(Instant.now()));
		temp = cdao.save(comment);
		sendMail("ciro.dalessandro@outlook.it", "Test commento", "E' stata creato/modificato un nuovo commento");
		return temp;
	}

	@Override
	public Comment getId(int id) {
		return cdao.findOne(id);
	}

	@Override
	public void deleteId(int id) {
		cdao.delete(id);
		sendMail("ciro.dalessandro@outlook.it", "Test commento", "E' stato eliminato ");
	}

	@Override
	public List<Comment> list() {
		return (List<Comment>) cdao.findAll();
	}

	@Override
	public List<Comment> listEvaluating() {
		return cdao.getCommentByAccepted(false);
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
		Comment temp = new Comment();
		temp = cdao.save(comment);
		return temp;
	}

}
