package com.onnisoft.wahoo.model.document;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.onnisoft.wahoo.model.document.enums.FootballMatchPhase;

@Document(collection = "event-status")
public final class EventStatus extends Node implements Serializable {

	private static final long serialVersionUID = -8024640657760718677L;

	private FootballMatchPhase footballMatchPhase;
	private Date startTime;
	private int minute;
	private int homeScore;
	private int awayScore;
	private int homeRedCards;
	private int awayRedCards;

	/**
	 * Used for json serialization/deserialization.
	 */
	public EventStatus() {
	}

	private EventStatus(Builder builder) {
		super(builder.id, builder.creationDate, builder.updateDate);
		this.footballMatchPhase = builder.footballMatchPhase;
		this.startTime = builder.startTime;
		this.minute = builder.minute;
		this.homeScore = builder.homeScore;
		this.awayScore = builder.awayScore;
		this.homeRedCards = builder.homeRedCards;
		this.awayRedCards = builder.awayRedCards;
	}

	public FootballMatchPhase getFootballMatchPhase() {
		return footballMatchPhase;
	}

	public Date getStartTime() {
		return startTime;
	}

	public int getMinute() {
		return minute;
	}

	public int getHomeScore() {
		return homeScore;
	}

	public int getAwayScore() {
		return awayScore;
	}

	public int getHomeRedCards() {
		return homeRedCards;
	}

	public int getAwayRedCards() {
		return awayRedCards;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + awayRedCards;
		result = prime * result + awayScore;
		result = prime * result + ((footballMatchPhase == null) ? 0 : footballMatchPhase.hashCode());
		result = prime * result + homeRedCards;
		result = prime * result + homeScore;
		result = prime * result + minute;
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
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
		EventStatus other = (EventStatus) obj;
		if (awayRedCards != other.awayRedCards)
			return false;
		if (awayScore != other.awayScore)
			return false;
		if (footballMatchPhase != other.footballMatchPhase)
			return false;
		if (homeRedCards != other.homeRedCards)
			return false;
		if (homeScore != other.homeScore)
			return false;
		if (minute != other.minute)
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		return true;
	}

	public static final class Builder {
		private String id;
		private FootballMatchPhase footballMatchPhase;
		private Date startTime;
		private int minute;
		private int homeScore;
		private int awayScore;
		private int homeRedCards;
		private int awayRedCards;

		private boolean isCreated;

		private Date creationDate;
		private Date updateDate;

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder footballMatchPhase(FootballMatchPhase footballMatchPhase) {
			this.footballMatchPhase = footballMatchPhase;
			return this;
		}

		public Builder startTime(Date startTime) {
			this.startTime = startTime;
			return this;
		}

		public Builder minute(int minute) {
			this.minute = minute;
			return this;
		}

		public Builder homeScore(int homeScore) {
			this.homeScore = homeScore;
			return this;
		}

		public Builder awayScore(int awayScore) {
			this.awayScore = awayScore;
			return this;
		}

		public Builder homeRedCards(int homeRedCards) {
			this.homeRedCards = homeRedCards;
			return this;
		}

		public Builder awayRedCards(int awayRedCards) {
			this.awayRedCards = awayRedCards;
			return this;
		}

		public Builder toCreate() {
			this.isCreated = true;
			return this;
		}

		public EventStatus build() {
			if (isCreated) {
				this.creationDate = new Date();
			} else {
				this.updateDate = new Date();
			}
			return new EventStatus(this);
		}
	}
}