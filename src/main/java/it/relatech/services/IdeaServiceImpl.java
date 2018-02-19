package it.relatech.services;

import java.sql.Timestamp;
import java.time.Instant;
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
	public Idea save(Idea idea) {
		Idea temp = new Idea();
		idea.setDateIdea(Timestamp.from(Instant.now()));
		temp = idao.save(idea);
		sendMail("Test idea", "E' stata creata/modificata una nuova idea");
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
		return idea.getComlist();
	}

	@Override
	public Idea vote(Idea c, int voto) {
		if (voto >= 1 && voto <= 5) {
			Idea idea = idao.getIdeaById(c.getId());
			double avg = idea.getVoteaverage();
			int cont = idea.getVotecounter();

			avg = (avg * cont + voto) / (cont + 1);
			cont++;

			idea.setVoteaverage(avg);
			idea.setVotecounter(cont);

			return update(idea);
		}

		return null;

	}

	@Override
	public Idea accept(Idea idea) {
		Idea ide = idao.getIdeaById(idea.getId());
		ide.setAccepted(true);
		return save(ide);
	}

	@Override
	public Idea update(Idea idea) {
		Idea temp = new Idea();
		idea.setDateIdea(Timestamp.from(Instant.now()));
		idea.setAccepted(false);
		temp = idao.save(idea);
		return temp;
	}

	@Override
	public List<Idea> listNotAccepted() {
		return idao.getIdeaByAccepted(false);
	}

}
