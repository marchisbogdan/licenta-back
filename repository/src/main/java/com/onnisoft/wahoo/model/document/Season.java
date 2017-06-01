package com.onnisoft.wahoo.model.document;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
 * Entity that keeps information about seasons.
 *
 * @author mbozesan
 * @date 30 Sep 2016 - 13:28:38
 *
 */
@Document(collection = "seasons")
public class Season extends Node implements Serializable {

	private static final long serialVersionUID = -2222690824740289883L;

	private String name;
	@DBRef
	private RealCompetition competition;

	private Integer rounds;
	private Integer numTeams;

	public Season() {
	}

	private Season(Builder builder) {
		super(builder.id, builder.creationDate, builder.updateDate);
		this.name = builder.name;
		this.competition = builder.competition;
		this.rounds = builder.rounds;
		this.numTeams = builder.numTeams;

	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the competition
	 */
	public RealCompetition getCompetition() {
		return competition;
	}

	/**
	 * 
	 * @return the number of rounds
	 */
	public Integer getRounds() {
		return rounds;
	}

	/**
	 * 
	 * @return number of teams
	 */
	public Integer getNumTeams() {
		return numTeams;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((competition == null) ? 0 : competition.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((numTeams == null) ? 0 : numTeams.hashCode());
		result = prime * result + ((rounds == null) ? 0 : rounds.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Season other = (Season) obj;
		if (competition == null) {
			if (other.competition != null)
				return false;
		} else if (!competition.equals(other.competition))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (numTeams == null) {
			if (other.numTeams != null)
				return false;
		} else if (!numTeams.equals(other.numTeams))
			return false;
		if (rounds == null) {
			if (other.rounds != null)
				return false;
		} else if (!rounds.equals(other.rounds))
			return false;
		return true;
	}

	public static final class Builder {
		private String id;
		private String name;
		private RealCompetition competition;

		private Integer rounds;
		private Integer numTeams;

		private boolean isCreated;

		private Date creationDate;
		private Date updateDate;

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder competition(RealCompetition competition) {
			this.competition = competition;
			return this;
		}

		public Builder rounds(int rounds) {
			this.rounds = rounds;
			return this;
		}

		public Builder numTeam(int numTeams) {
			this.numTeams = numTeams;
			return this;
		}

		public Builder toCreate() {
			this.isCreated = true;
			return this;
		}

		public Season builder() {
			if (isCreated) {
				this.creationDate = new Date();
			} else {
				this.updateDate = new Date();
			}
			return new Season(this);
		}
	}
}
