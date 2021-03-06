package it.relatech.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.relatech.repository.CommentDao;
import it.relatech.repository.UserDao;
import it.relatech.mail.EmailConfig;
import it.relatech.mail.MailObject;
import it.relatech.model.Comment;
import it.relatech.model.User;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentDao cdao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private EmailConfig emailService;

	public void sendMail(String oggetto, String testo) {
		List<User> destinatari = (List<User>) userDao.findAll();

		if (!destinatari.isEmpty()) {
			for (int i = 0; i < destinatari.size(); i++) {
				MailObject mailObject = new MailObject();
				mailObject.setTo(destinatari.get(i).getMail());
				mailObject.setSubject(oggetto);
				mailObject.setText(testo);
				emailService.sendSimpleMessage(mailObject);
			}
		}
	}

	@Override
	public Comment save(Comment comment) throws Exception {
		if(comment.getId() != 0 || comment.isAccepted())
			return cdao.save(comment);
		if (comment.getText() == null)
			throw new Exception("Testo commento nullo");

		Comment temp = new Comment();
		comment.setDateComment(Timestamp.from(Instant.now()));
		temp = cdao.save(comment);
		if (!temp.isAccepted()) {
			sendMail("Test commento", "E' stata creato/modificato un nuovo commento");
		}
		return temp;
	}

	@Override
	public Comment getById(int id) {
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
		return cdao.getCommentByAccepted(false);
	}

	@Override
	public Comment accept(int id) throws Exception {
		Comment com = cdao.getCommentById(id);
		if(com.getId() != 0) {
			com.setAccepted(true);
			return save(com);
		}
		else 
			return com;
	}

	@Override
	public Comment update(Comment comment) throws Exception {
		Comment temp = cdao.getCommentById(comment.getId());
		temp.setText(comment.getText());
		return cdao.save(temp);
	}

}
