package it.relatech.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.relatech.util.JsonDateSerializer;

@Entity
public class Idea {

	@Id
	@GeneratedValue
	private int id;

	private String text;

	@JsonSerialize(using = JsonDateSerializer.class)
	private Timestamp dateIdea;

	private boolean accepted;
	private double voteaverage;
	private int votecounter;

	// @JsonIgnore
	@JsonManagedReference
	@OneToMany(mappedBy = "idea", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Comment> comlist;

	// --------------------------------------------------------------
	public Idea() {
		this.comlist = new ArrayList<Comment>();
		this.accepted = false;
		this.voteaverage = 0;
		this.votecounter = 0;
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

	public Timestamp getDateIdea() {
		return dateIdea;
	}

	public void setDateIdea(Timestamp dateIdea) {
		this.dateIdea = dateIdea;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public double getVoteaverage() {
		return voteaverage;
	}

	public void setVoteaverage(double voteaverage) {
		this.voteaverage = voteaverage;
	}

	public int getVotecounter() {
		return votecounter;
	}

	public void setVotecounter(int votecounter) {
		this.votecounter = votecounter;
	}

	public List<Comment> getComlist() {
		return comlist;
	}

	public void setComlist(List<Comment> comlist) {
		this.comlist = comlist;
	}

	@Override
	public String toString() {
		return "Idea [id=" + id + ", text=" + text + ", dateIdea=" + dateIdea + ", accepted=" + accepted
				+ ", voteaverage=" + voteaverage + ", votecounter=" + votecounter + "]";
	}

}
