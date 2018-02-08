package it.relatech.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.relatech.model.Idea;

@Repository
public interface IdeaDao extends CrudRepository<Idea, Integer> {

	Idea getIdeaById(int id);

	List<Idea> getIdeaByAccepted(boolean accepted);

}
