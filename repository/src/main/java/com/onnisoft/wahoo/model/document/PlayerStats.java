package com.onnisoft.wahoo.model.document;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "players-stats")
public class PlayerStats extends Node implements Serializable {

	private static final long serialVersionUID = -5517928194615359184L;

	@DBRef
	private Player player;

	@DBRef
	private Season season;

	@DBRef
	private RealCompetitor realCompetitor;

	private int matchesPlayed;
	private int minutesPlayed;
	private int goals;
	private int assists;
	private int yellowCards;
	private int redCards;
	private int lineupStarts;
	private int substitutedIn;
	private int substitutedOut;
	private int value;
	private int totalPoints;

	public PlayerStats() {

	}

	private PlayerStats(Builder builder) {
		super(builder.id, builder.creationDate, builder.updateDate);
		this.player = builder.player;
		this.season = builder.season;
		this.realCompetitor = builder.realCompetitor;
		this.matchesPlayed = builder.matchesPlayed;
		this.minutesPlayed = builder.minutesPlayed;
		this.goals = builder.goals;
		this.assists = builder.assists;
		this.yellowCards = builder.yellowCards;
		this.redCards = builder.redCards;
		this.lineupStarts = builder.lineupStarts;
		this.substitutedIn = builder.substitutedIn;
		this.substitutedOut = builder.substitutedOut;
		this.value = builder.value;
		this.totalPoints = builder.totalPoints;
	}

	public Player getPlayer() {
		return player;
	}

	public Season getSeason() {
		return season;
	}

	public RealCompetitor getRealCompetitor() {
		return realCompetitor;
	}

	public int getMatchesPlayed() {
		return matchesPlayed;
	}

	public int getMinutesPlayed() {
		return minutesPlayed;
	}

	public int getGoals() {
		return goals;
	}

	public int getAssists() {
		return assists;
	}

	public int getYellowCards() {
		return yellowCards;
	}

	public int getRedCards() {
		return redCards;
	}

	public int getLineupStarts() {
		return lineupStarts;
	}

	public int getSubstitutedIn() {
		return substitutedIn;
	}

	public int getSubstitutedOut() {
		return substitutedOut;
	}

	public int getValue() {
		return value;
	}

	public int getTotalPoints() {
		return totalPoints;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + assists;
		result = prime * result + goals;
		result = prime * result + lineupStarts;
		result = prime * result + matchesPlayed;
		result = prime * result + minutesPlayed;
		result = prime * result + ((player == null) ? 0 : player.hashCode());
		result = prime * result + ((realCompetitor == null) ? 0 : realCompetitor.hashCode());
		result = prime * result + redCards;
		result = prime * result + ((season == null) ? 0 : season.hashCode());
		result = prime * result + substitutedIn;
		result = prime * result + substitutedOut;
		result = prime * result + totalPoints;
		result = prime * result + value;
		result = prime * result + yellowCards;
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
		PlayerStats other = (PlayerStats) obj;
		if (assists != other.assists)
			return false;
		if (goals != other.goals)
			return false;
		if (lineupStarts != other.lineupStarts)
			return false;
		if (matchesPlayed != other.matchesPlayed)
			return false;
		if (minutesPlayed != other.minutesPlayed)
			return false;
		if (player == null) {
			if (other.player != null)
				return false;
		} else if (!player.equals(other.player))
			return false;
		if (realCompetitor == null) {
			if (other.realCompetitor != null)
				return false;
		} else if (!realCompetitor.equals(other.realCompetitor))
			return false;
		if (redCards != other.redCards)
			return false;
		if (season == null) {
			if (other.season != null)
				return false;
		} else if (!season.equals(other.season))
			return false;
		if (substitutedIn != other.substitutedIn)
			return false;
		if (substitutedOut != other.substitutedOut)
			return false;
		if (totalPoints != other.totalPoints)
			return false;
		if (value != other.value)
			return false;
		if (yellowCards != other.yellowCards)
			return false;
		return true;
	}

	public static final class Builder {
		private String id;
		private Player player;
		private Season season;
		private RealCompetitor realCompetitor;
		private int matchesPlayed;
		private int minutesPlayed;
		private int goals;
		private int assists;
		private int yellowCards;
		private int redCards;
		private int lineupStarts;
		private int substitutedIn;
		private int substitutedOut;
		private int value;
		private int totalPoints;

		private boolean isCreated;

		private Date creationDate;
		private Date updateDate;

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder player(Player player) {
			this.player = player;
			return this;
		}

		public Builder season(Season season) {
			this.season = season;
			return this;
		}

		public Builder realCompetitor(RealCompetitor realCompetitor) {
			this.realCompetitor = realCompetitor;
			return this;
		}

		public Builder matchesPlayed(int matchesPlayed) {
			this.matchesPlayed = matchesPlayed;
			return this;
		}

		public Builder minutesPlayed(int minutesPlayed) {
			this.minutesPlayed = minutesPlayed;
			return this;
		}

		public Builder goals(int goals) {
			this.goals = goals;
			return this;
		}

		public Builder assists(int assists) {
			this.assists = assists;
			return this;
		}

		public Builder yellowCards(int yellowCards) {
			this.yellowCards = yellowCards;
			return this;
		}

		public Builder redCards(int redCards) {
			this.redCards = redCards;
			return this;
		}

		public Builder lineupStarts(int lineupStarts) {
			this.lineupStarts = lineupStarts;
			return this;
		}

		public Builder substitutedIn(int substitutedIn) {
			this.substitutedIn = substitutedIn;
			return this;
		}

		public Builder substitutedOut(int substitutedOut) {
			this.substitutedOut = substitutedOut;
			return this;
		}

		public Builder value(int value) {
			this.value = value;
			return this;
		}

		public Builder totalPoints(int totalPoints) {
			this.totalPoints = totalPoints;
			return this;
		}

		public Builder toCreate() {
			this.isCreated = true;
			return this;
		}

		public PlayerStats build() {
			if (isCreated) {
				this.creationDate = new Date();
			} else {
				this.updateDate = new Date();
			}
			return new PlayerStats(this);
		}
	}
}
