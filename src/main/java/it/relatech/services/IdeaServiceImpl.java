package it.relatech.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.relatech.repository.IdeaDao;
import it.relatech.model.Idea;

@Service
public class IdeaServiceImpl implements IdeaService {

	@Autowired
	private IdeaDao idao;

	@Override
	public Idea save(Idea idea) {
		idea.setDateIdea(Timestamp.from(Instant.now()));
		return idao.save(idea);
	}

	@Override
	public void deleteId(int id) {
		idao.delete(id);
	}

	@Override
	public Idea getId(int id) {
		return idao.findOne(id);
	}

	@Override
	public List<Idea> list() {
		return (List<Idea>) idao.findAll();
	}

	@Override
	public List<Idea> listAccepted() {
		return idao.getIdeaByAccepted(true);
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
		return update(ide);
	}

	// TODO: Unire al save
	@Override
	public Idea update(Idea idea) {
		idea.setDateIdea(Timestamp.from(Instant.now()));
		return idao.save(idea);
	}

}
