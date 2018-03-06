package it.relatech.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.relatech.repository.IdeaDao;
import it.relatech.repository.UserDao;
import it.relatech.mail.EmailConfig;
import it.relatech.mail.MailObject;
import it.relatech.model.Comment;
import it.relatech.model.Idea;
import it.relatech.model.User;

@Service
public class IdeaServiceImpl implements IdeaService {

	@Autowired
	private IdeaDao idao;

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
	public Idea save(Idea idea) throws Exception {
		if(idea.getId() != 0 || idea.isAccepted())
			return idao.save(idea);
		if (idea.getText() == null)
			throw new Exception("Testo idea nullo");

		Idea temp = new Idea();
		idea.setDateIdea(Timestamp.from(Instant.now()));
		temp = idao.save(idea);
		if (!temp.isAccepted()) {
			sendMail("Test idea", "E' stata creata/modificata una nuova idea");
		}
		return temp;
	}

	@Override
	public void deleteId(int id) {
		idao.delete(id);
	}

	@Override
	public Idea getById(int id) {
		return idao.findOne(id);
	}

	@Override
	public List<Idea> list() {
		return (List<Idea>) idao.findAll();
	}

	@Override
	public List<Idea> listAccepted() {
		List<Idea> temp = idao.getIdeaByAccepted(true);
		int temp_size = temp.size();

		// Controlla i commenti accettati
		for (int i = 0; i < temp_size; i++) {
			int comment_size = temp.get(i).getComlist().size();
			for (int j = comment_size - 1; j >= 0; j--) {
				if (!temp.get(i).getComlist().get(j).isAccepted())
					temp.get(i).getComlist().remove(j);
			}
		}

		return temp;
	}

	@Override
	public List<Comment> getListComment(int id) {
		Idea idea = idao.findOne(id);
		List<Comment> commentAccepted = new LinkedList<>();

		// Controlla i commenti accettati
		for (Comment comment : idea.getComlist())
			if (comment.isAccepted())
				commentAccepted.add(comment);

		return commentAccepted;
	}

	@Override
	public Idea vote(Idea c, int voto) throws Exception {
		if (voto >= 1 && voto <= 5) {
			Idea idea = idao.getIdeaById(c.getId());
			double avg = idea.getVoteaverage();
			int cont = idea.getVotecounter();

			avg = (avg * cont + voto) / (cont + 1);
			cont++;

			idea.setVoteaverage(avg);
			idea.setVotecounter(cont);

			return save(idea);
		}

		throw new Exception("Voto non valido");

	}

	@Override
	public Idea accept(int id) throws Exception {
		Idea ide = idao.getIdeaById(id);
		if(ide.getId() != 0) {
			ide.setAccepted(true);
			return save(ide);
		}
		else 
			return null;
	}

	@Override
	public Idea update(Idea idea) {
		Idea temp = new Idea();
		idea.setDateIdea(Timestamp.from(Instant.now()));
		temp = idao.save(idea);
		return temp;
	}

	@Override
	public List<Idea> listNotAccepted() {
		return idao.getIdeaByAccepted(false);
	}

}
