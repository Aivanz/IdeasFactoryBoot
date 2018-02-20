package it.relatech.model;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.relatech.model.Idea;
import it.relatech.util.JsonDateSerializer;

@Entity
public class Comment {

	@Id
	@GeneratedValue
	private int id;

	@Size(max = 500)
	private String text;

	@JsonSerialize(using = JsonDateSerializer.class)
	private Timestamp dateComment;

	private boolean accepted;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	private Idea idea;

	public Comment() {
		this.accepted = false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Timestamp getDateComment() {
		return dateComment;
	}

	public void setDateComment(Timestamp dateComment) {
		this.dateComment = dateComment;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public Idea getIdea() {
		return idea;
	}

	public void setIdea(Idea idea) {
		this.idea = idea;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", text=" + text + ", dateComment=" + dateComment + ", accepted=" + accepted + "]";
	}

}
