package it.relatech.services;

import java.util.List;

import it.relatech.model.Idea;

public interface IdeaService {

	Idea save(Idea idea);

	Idea update(Idea idea);

	Idea getId(int id);

	void deleteId(int id);

	List<Idea> list();

	List<Idea> listAccepted();
}