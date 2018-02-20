package it.relatech.services;

import java.util.List;

import it.relatech.model.Comment;
import it.relatech.model.Idea;

public interface IdeaService {

	Idea save(Idea idea) throws Exception;

	Idea update(Idea idea);

	Idea getById(int id);

	void deleteId(int id);

	List<Idea> list();

	List<Idea> listAccepted();

	Idea vote(Idea c, int voto) throws Exception;

	Idea accept(int id) throws Exception;

	List<Comment> getListComment(int id);

	List<Idea> listNotAccepted();
}