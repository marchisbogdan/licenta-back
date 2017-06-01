package com.onnisoft.wahoo.model.document;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "real-competitors-seasons")
public class RealCompetitorSeason extends Node implements Serializable {

	private static final long serialVersionUID = -7614513880795614082L;

	@DBRef
	private RealCompetitor realCompetitor;

	@DBRef
	private Season season;

	private int numOfMatches;
	private int numOfGoalsScored;
	private int numOfGoalsConceded;
	private int numOfPoints;
	private int value;
	private int numOfHomeWins;
	private int numOfHomeLooses;
	private int numOfHomeDraws;
	private int numOfAwayWins;
	private int numOfAwayLooses;
	private int numOfAwayDraws;
	private int rank;
	private int cleanSheets;
	private int noGoalsScoredMatches;

	public RealCompetitorSeason() {

	}

	private RealCompetitorSeason(Builder builder) {
		super(builder.id, builder.creationDate, builder.updateDate);
		this.realCompetitor = builder.realCompetitor;
		this.season = builder.season;
		this.numOfMatches = builder.numOfMatches;
		this.numOfGoalsScored = builder.numOfGoalsScored;
		this.numOfGoalsConceded = builder.numOfGoalsConceded;
		this.numOfPoints = builder.numOfPoints;
		this.value = builder.value;
		this.numOfHomeWins = builder.numOfHomeWins;
		this.numOfHomeLooses = builder.numOfHomeLooses;
		this.numOfHomeDraws = builder.numOfHomeDraws;
		this.numOfAwayWins = builder.numOfAwayWins;
		this.numOfAwayLooses = builder.numOfAwayLooses;
		this.numOfAwayDraws = builder.numOfAwayDraws;
		this.rank = builder.rank;
		this.cleanSheets = builder.cleanSheets;
		this.noGoalsScoredMatches = builder.noGoalsScoredMatches;
	}

	public RealCompetitor getRealCompetitor() {
		return realCompetitor;
	}

	public Season getSeason() {
		return season;
	}

	public int getNumOfMatches() {
		return numOfMatches;
	}

	public int getNumOfGoalsScored() {
		return numOfGoalsScored;
	}

	public int getNumOfGoalsConceded() {
		return numOfGoalsConceded;
	}

	public int getNumOfPoints() {
		return numOfPoints;
	}

	public int getValue() {
		return value;
	}

	public int getNumOfHomeWins() {
		return numOfHomeWins;
	}

	public int getNumOfHomeLooses() {
		return numOfHomeLooses;
	}

	public int getNumOfHomeDraws() {
		return numOfHomeDraws;
	}

	public int getNumOfAwayWins() {
		return numOfAwayWins;
	}

	public int getNumOfAwayLooses() {
		return numOfAwayLooses;
	}

	public int getNumOfAwayDraws() {
		return numOfAwayDraws;
	}

	public int getRank() {
		return rank;
	}

	public int getCleanSheets() {
		return cleanSheets;
	}

	public int getNoGoalsScoredMatches() {
		return noGoalsScoredMatches;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + cleanSheets;
		result = prime * result + noGoalsScoredMatches;
		result = prime * result + numOfAwayDraws;
		result = prime * result + numOfAwayLooses;
		result = prime * result + numOfAwayWins;
		result = prime * result + numOfGoalsConceded;
		result = prime * result + numOfGoalsScored;
		result = prime * result + numOfHomeDraws;
		result = prime * result + numOfHomeLooses;
		result = prime * result + numOfHomeWins;
		result = prime * result + numOfMatches;
		result = prime * result + numOfPoints;
		result = prime * result + rank;
		result = prime * result + ((realCompetitor == null) ? 0 : realCompetitor.hashCode());
		result = prime * result + ((season == null) ? 0 : season.hashCode());
		result = prime * result + value;
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
		RealCompetitorSeason other = (RealCompetitorSeason) obj;
		if (cleanSheets != other.cleanSheets)
			return false;
		if (noGoalsScoredMatches != other.noGoalsScoredMatches)
			return false;
		if (numOfAwayDraws != other.numOfAwayDraws)
			return false;
		if (numOfAwayLooses != other.numOfAwayLooses)
			return false;
		if (numOfAwayWins != other.numOfAwayWins)
			return false;
		if (numOfGoalsConceded != other.numOfGoalsConceded)
			return false;
		if (numOfGoalsScored != other.numOfGoalsScored)
			return false;
		if (numOfHomeDraws != other.numOfHomeDraws)
			return false;
		if (numOfHomeLooses != other.numOfHomeLooses)
			return false;
		if (numOfHomeWins != other.numOfHomeWins)
			return false;
		if (numOfMatches != other.numOfMatches)
			return false;
		if (numOfPoints != other.numOfPoints)
			return false;
		if (rank != other.rank)
			return false;
		if (realCompetitor == null) {
			if (other.realCompetitor != null)
				return false;
		} else if (!realCompetitor.equals(other.realCompetitor))
			return false;
		if (season == null) {
			if (other.season != null)
				return false;
		} else if (!season.equals(other.season))
			return false;
		if (value != other.value)
			return false;
		return true;
	}

	public static final class Builder {
		private String id;
		private RealCompetitor realCompetitor;
		private Season season;
		private int numOfMatches;
		private int numOfGoalsScored;
		private int numOfGoalsConceded;
		private int numOfPoints;
		private int value;
		private int numOfHomeWins;
		private int numOfHomeLooses;
		private int numOfHomeDraws;
		private int numOfAwayWins;
		private int numOfAwayLooses;
		private int numOfAwayDraws;
		private int rank;
		private int cleanSheets;
		private int noGoalsScoredMatches;

		private boolean isCreated;

		private Date creationDate;
		private Date updateDate;

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder realCompetitor(RealCompetitor realCompetitor) {
			this.realCompetitor = realCompetitor;
			return this;
		}

		public Builder season(Season season) {
			this.season = season;
			return this;
		}

		public Builder numOfMatches(int numOfMatches) {
			this.numOfMatches = numOfMatches;
			return this;
		}

		public Builder numOfGoalsScored(int numOfGoalsScored) {
			this.numOfGoalsScored = numOfGoalsScored;
			return this;
		}

		public Builder numOfGoalsConceded(int numOfGoalsConceded) {
			this.numOfGoalsConceded = numOfGoalsConceded;
			return this;
		}

		public Builder numOfPoints(int numOfPoints) {
			this.numOfPoints = numOfPoints;
			return this;
		}

		public Builder value(int value) {
			this.value = value;
			return this;
		}

		public Builder numOfHomeWins(int numOfHomeWins) {
			this.numOfHomeWins = numOfHomeWins;
			return this;
		}

		public Builder numOfHomeLooses(int numOfHomeLooses) {
			this.numOfHomeLooses = numOfHomeLooses;
			return this;
		}

		public Builder numOfHomeDraws(int numOfHomeDraws) {
			this.numOfHomeDraws = numOfHomeDraws;
			return this;
		}

		public Builder numOfAwayWins(int numOfAwayWins) {
			this.numOfAwayWins = numOfAwayWins;
			return this;
		}

		public Builder numOfAwayLooses(int numOfAwayLooses) {
			this.numOfAwayLooses = numOfAwayLooses;
			return this;
		}

		public Builder numOfAwayDraws(int numOfAwayDraws) {
			this.numOfAwayDraws = numOfAwayDraws;
			return this;
		}

		public Builder rank(int rank) {
			this.rank = rank;
			return this;
		}

		public Builder cleanSheets(int cleanSheets) {
			this.cleanSheets = cleanSheets;
			return this;
		}

		public Builder noGoalsScoredMatches(int noGoalsScoredMatches) {
			this.noGoalsScoredMatches = noGoalsScoredMatches;
			return this;
		}

		public Builder toCreate() {
			this.isCreated = true;
			return this;
		}

		public RealCompetitorSeason build() {
			if (isCreated) {
				this.creationDate = new Date();
			} else {
				this.updateDate = new Date();
			}
			return new RealCompetitorSeason(this);
		}
	}
}
