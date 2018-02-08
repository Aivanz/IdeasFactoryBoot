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

	// TODO: Unire al save
	@Override
	public Idea update(Idea idea) {
		idea.setDateIdea(Timestamp.from(Instant.now()));
		return idao.save(idea);
	}

}
