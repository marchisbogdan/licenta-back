package com.onnisoft.wahoo.model.document;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Model for statistics of each player linked to the event.
 *
 * @author alexandru.mos
 * @date Feb 10, 2017 - 12:13:19 PM
 *
 */
@Document(collection = "player-event-statistics")
public final class PlayerEventStatistics extends Node implements Serializable {

	private static final long serialVersionUID = 1320483429276490828L;

	@DBRef
	private Event event;
	@DBRef
	private RealCompetitor competitor;
	@DBRef
	private Player player;

	private boolean gameStarted;
	private boolean totalSubOn;
	private boolean totalSubOff;
	private int minsPlayed;
	private int goals;
	private int goalAssist;
	private int ownGoals;
	private boolean cleanSheet;
	private boolean yellowCard;
	private boolean redCard;
	private boolean secondYellow;
	private int goalPlusminus;
	private int goalPlusminusFor;
	private int goalPlusminusAgainst;
	private int penGoals;

	private int points;
	private int value;

	/**
	 * Used for json serialization/deserialization.
	 */
	public PlayerEventStatistics() {
	}

	/**
	 * @param builder
	 *            - the builder factory used to initialize the
	 *            {@link PlayerEventStatistics} attributes.
	 */
	private PlayerEventStatistics(Builder builder) {
		super(builder.id, builder.creationDate, builder.updateDate);
		this.event = builder.event;
		this.competitor = builder.competitor;
		this.player = builder.player;
		this.gameStarted = builder.gameStarted;
		this.totalSubOn = builder.totalSubOn;
		this.totalSubOff = builder.totalSubOff;
		this.minsPlayed = builder.minsPlayed;
		this.goals = builder.goals;
		this.goalAssist = builder.goalAssist;
		this.ownGoals = builder.ownGoals;
		this.cleanSheet = builder.cleanSheet;
		this.yellowCard = builder.yellowCard;
		this.redCard = builder.redCard;
		this.secondYellow = builder.secondYellow;
		this.goalPlusminus = builder.goalPlusminus;
		this.goalPlusminusFor = builder.goalPlusminusFor;
		this.goalPlusminusAgainst = builder.goalPlusminusAgainst;
		this.penGoals = builder.penGoals;
		this.value = builder.value;
		this.points = builder.points;
	}

	/**
	 * @return the event
	 */
	public Event getEvent() {
		return this.event;
	}

	/**
	 * @return the competitor
	 */
	public RealCompetitor getCompetitor() {
		return this.competitor;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return this.player;
	}

	/**
	 * @return the gameStarted
	 */
	public boolean isGameStarted() {
		return this.gameStarted;
	}

	/**
	 * @return the totalSubOn
	 */
	public boolean isTotalSubOn() {
		return this.totalSubOn;
	}

	/**
	 * @return the totalSubOff
	 */
	public boolean isTotalSubOff() {
		return this.totalSubOff;
	}

	/**
	 * @return the minsPlayed
	 */
	public int getMinsPlayed() {
		return this.minsPlayed;
	}

	/**
	 * @return the goals
	 */
	public int getGoals() {
		return this.goals;
	}

	/**
	 * @return the goalAssist
	 */
	public int getGoalAssist() {
		return this.goalAssist;
	}

	/**
	 * @return the ownGoals
	 */
	public int getOwnGoals() {
		return this.ownGoals;
	}

	/**
	 * @return the cleanSheet
	 */
	public boolean getCleanSheet() {
		return this.cleanSheet;
	}

	/**
	 * @return the yellowCard
	 */
	public boolean isYellowCard() {
		return this.yellowCard;
	}

	/**
	 * @return the redCard
	 */
	public boolean isRedCard() {
		return this.redCard;
	}

	/**
	 * @return the secondYellow
	 */
	public boolean isSecondYellow() {
		return this.secondYellow;
	}

	/**
	 * @return the goalPlusminus
	 */
	public int getGoalPlusminus() {
		return this.goalPlusminus;
	}

	/**
	 * @return the goalPlusminusFor
	 */
	public int getGoalPlusminusFor() {
		return this.goalPlusminusFor;
	}

	/**
	 * @return the goalPlusminusAgainst
	 */
	public int getGoalPlusminusAgainst() {
		return this.goalPlusminusAgainst;
	}

	/**
	 * @return the penGoals
	 */
	public int getPenGoals() {
		return this.penGoals;
	}

	/**
	 * 
	 * @return the points
	 */
	public int getPoints() {
		return this.points;
	}

	/**
	 * 
	 * @return the value
	 */
	public int getValue() {
		return this.value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (cleanSheet ? 1231 : 1237);
		result = prime * result + ((competitor == null) ? 0 : competitor.hashCode());
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		result = prime * result + (gameStarted ? 1231 : 1237);
		result = prime * result + goalAssist;
		result = prime * result + goalPlusminus;
		result = prime * result + goalPlusminusAgainst;
		result = prime * result + goalPlusminusFor;
		result = prime * result + goals;
		result = prime * result + minsPlayed;
		result = prime * result + ownGoals;
		result = prime * result + penGoals;
		result = prime * result + ((player == null) ? 0 : player.hashCode());
		result = prime * result + points;
		result = prime * result + (redCard ? 1231 : 1237);
		result = prime * result + (secondYellow ? 1231 : 1237);
		result = prime * result + (totalSubOff ? 1231 : 1237);
		result = prime * result + (totalSubOn ? 1231 : 1237);
		result = prime * result + value;
		result = prime * result + (yellowCard ? 1231 : 1237);
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
		PlayerEventStatistics other = (PlayerEventStatistics) obj;
		if (cleanSheet != other.cleanSheet)
			return false;
		if (competitor == null) {
			if (other.competitor != null)
				return false;
		} else if (!competitor.equals(other.competitor))
			return false;
		if (event == null) {
			if (other.event != null)
				return false;
		} else if (!event.equals(other.event))
			return false;
		if (gameStarted != other.gameStarted)
			return false;
		if (goalAssist != other.goalAssist)
			return false;
		if (goalPlusminus != other.goalPlusminus)
			return false;
		if (goalPlusminusAgainst != other.goalPlusminusAgainst)
			return false;
		if (goalPlusminusFor != other.goalPlusminusFor)
			return false;
		if (goals != other.goals)
			return false;
		if (minsPlayed != other.minsPlayed)
			return false;
		if (ownGoals != other.ownGoals)
			return false;
		if (penGoals != other.penGoals)
			return false;
		if (player == null) {
			if (other.player != null)
				return false;
		} else if (!player.equals(other.player))
			return false;
		if (points != other.points)
			return false;
		if (redCard != other.redCard)
			return false;
		if (secondYellow != other.secondYellow)
			return false;
		if (totalSubOff != other.totalSubOff)
			return false;
		if (totalSubOn != other.totalSubOn)
			return false;
		if (value != other.value)
			return false;
		if (yellowCard != other.yellowCard)
			return false;
		return true;
	}

	public static final class Builder {

		private String id;

		private Event event;
		private RealCompetitor competitor;
		private Player player;

		private boolean gameStarted;
		private boolean totalSubOn;
		private boolean totalSubOff;
		private int minsPlayed;
		private int goals;
		private int goalAssist;
		private int ownGoals;
		private boolean cleanSheet;
		private boolean yellowCard;
		private boolean redCard;
		private boolean secondYellow;
		private int goalPlusminus;
		private int goalPlusminusFor;
		private int goalPlusminusAgainst;
		private int penGoals;

		private int value;
		private int points;

		private boolean isCreated;

		private Date creationDate;
		private Date updateDate;

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder event(Event event) {
			this.event = event;
			return this;
		}

		public Builder competitor(RealCompetitor competitor) {
			this.competitor = competitor;
			return this;
		}

		public Builder player(Player player) {
			this.player = player;
			return this;
		}

		public Builder gameStarted(boolean gameStarted) {
			this.gameStarted = gameStarted;
			return this;
		}

		public Builder totalSubOn(boolean totalSubOn) {
			this.totalSubOn = totalSubOn;
			return this;
		}

		public Builder totalSubOff(boolean totalSubOff) {
			this.totalSubOff = totalSubOff;
			return this;
		}

		public Builder minsPlayed(int minsPlayed) {
			this.minsPlayed = minsPlayed;
			return this;
		}

		public Builder goals(int goals) {
			this.goals = goals;
			return this;
		}

		public Builder goalAssist(int goalAssist) {
			this.goalAssist = goalAssist;
			return this;
		}

		public Builder ownGoals(int ownGoals) {
			this.ownGoals = ownGoals;
			return this;
		}

		public Builder cleanSheet(boolean cleanSheet) {
			this.cleanSheet = cleanSheet;
			return this;
		}

		public Builder yellowCard(boolean yellowCard) {
			this.yellowCard = yellowCard;
			return this;
		}

		public Builder redCard(boolean redCard) {
			this.redCard = redCard;
			return this;
		}

		public Builder secondYellow(boolean secondYellow) {
			this.secondYellow = secondYellow;
			return this;
		}

		public Builder goalPlusminus(int goalPlusminus) {
			this.goalPlusminus = goalPlusminus;
			return this;
		}

		public Builder goalPlusminusFor(int goalPlusminusFor) {
			this.goalPlusminusFor = goalPlusminusFor;
			return this;
		}

		public Builder goalPlusminusAgainst(int goalPlusminusAgainst) {
			this.goalPlusminusAgainst = goalPlusminusAgainst;
			return this;
		}

		public Builder penGoals(int penGoals) {
			this.penGoals = penGoals;
			return this;
		}

		public Builder value(int value) {
			this.value = value;
			return this;
		}

		public Builder points(int points) {
			this.points = points;
			return this;
		}

		public Builder toCreate() {
			this.isCreated = true;
			return this;
		}

		public PlayerEventStatistics build() {
			if (isCreated) {
				this.creationDate = new Date();
			} else {
				this.updateDate = new Date();
			}
			return new PlayerEventStatistics(this);
		}
	}
}
