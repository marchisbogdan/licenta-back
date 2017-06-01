package com.onnisoft.wahoo.model.document;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
 * Entity that keeps information about the Points balance. This means the amount
 * of credit points and game points a player has.
 *
 * @author mbozesan
 * @date 23 Sep 2016 - 16:51:54
 *
 */
@Document(collection = "points")
public class Points extends Node implements Serializable {

	private static final long serialVersionUID = 5469097714213614259L;

	@DBRef
	private Subscriber subscriber;
	private Double crediBalance;
	private Double inGame;
	private Double byInteractions;
	private Double byReferrals;
	private Double usedOnRewards;
	private Double gamePoints;

	public Points() {
	}

	private Points(Builder builder) {
		super(builder.id, builder.creationDate, builder.updateDate);
		this.subscriber = builder.subscriber;
		this.crediBalance = builder.crediBalance;
		this.inGame = builder.inGame;
		this.byInteractions = builder.byInteractions;
		this.byReferrals = builder.byReferrals;
		this.usedOnRewards = builder.usedOnRewards;
		this.gamePoints = builder.gamePoints;
	}

	/**
	 * 
	 * @return subscriber
	 */
	public Subscriber getSubscriber() {
		return subscriber;
	}

	/**
	 * 
	 * @return byInteractions
	 */
	public Double getByinteractions() {
		return byInteractions;
	}

	/**
	 * 
	 * @return byReferrals
	 */
	public Double getByreferrals() {
		return byReferrals;
	}

	/**
	 * 
	 * @return usedOnRewards
	 */
	public Double getUsedOnRewards() {
		return usedOnRewards;
	}

	/**
	 * 
	 * @return gamePoints
	 */
	public Double getGamePoints() {
		return gamePoints;
	}

	/**
	 * @return the crediBalance
	 */
	public Double getBalance() {
		return crediBalance;
	}

	/**
	 * @return the inGame
	 */
	public Double getInGame() {
		return inGame;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((byInteractions == null) ? 0 : byInteractions.hashCode());
		result = prime * result + ((byReferrals == null) ? 0 : byReferrals.hashCode());
		result = prime * result + ((crediBalance == null) ? 0 : crediBalance.hashCode());
		result = prime * result + ((gamePoints == null) ? 0 : gamePoints.hashCode());
		result = prime * result + ((inGame == null) ? 0 : inGame.hashCode());
		result = prime * result + ((subscriber == null) ? 0 : subscriber.hashCode());
		result = prime * result + ((usedOnRewards == null) ? 0 : usedOnRewards.hashCode());
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
		Points other = (Points) obj;
		if (byInteractions == null) {
			if (other.byInteractions != null)
				return false;
		} else if (!byInteractions.equals(other.byInteractions))
			return false;
		if (byReferrals == null) {
			if (other.byReferrals != null)
				return false;
		} else if (!byReferrals.equals(other.byReferrals))
			return false;
		if (crediBalance == null) {
			if (other.crediBalance != null)
				return false;
		} else if (!crediBalance.equals(other.crediBalance))
			return false;
		if (gamePoints == null) {
			if (other.gamePoints != null)
				return false;
		} else if (!gamePoints.equals(other.gamePoints))
			return false;
		if (inGame == null) {
			if (other.inGame != null)
				return false;
		} else if (!inGame.equals(other.inGame))
			return false;
		if (subscriber == null) {
			if (other.subscriber != null)
				return false;
		} else if (!subscriber.equals(other.subscriber))
			return false;
		if (usedOnRewards == null) {
			if (other.usedOnRewards != null)
				return false;
		} else if (!usedOnRewards.equals(other.usedOnRewards))
			return false;
		return true;
	}

	public static final class Builder {
		private String id;
		private Subscriber subscriber;
		private Double crediBalance;
		private Double inGame;
		private Double byInteractions;
		private Double byReferrals;
		private Double usedOnRewards;
		private Double gamePoints;

		private boolean isCreated;

		private Date creationDate;
		private Date updateDate;

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder subscriber(Subscriber subscriber) {
			this.subscriber = subscriber;
			return this;
		}

		public Builder crediBalance(Double crediBalance) {
			this.crediBalance = crediBalance;
			return this;
		}

		public Builder inGame(Double inGame) {
			this.inGame = inGame;
			return this;
		}

		public Builder byInteractions(Double byInteractions) {
			this.byInteractions = byInteractions;
			return this;
		}

		public Builder byReferrals(Double byReferrals) {
			this.byReferrals = byReferrals;
			return this;
		}

		public Builder usedOnRewards(Double usedOnRewards) {
			this.usedOnRewards = usedOnRewards;
			return this;
		}

		public Builder gamePoints(Double gamePoints) {
			this.gamePoints = gamePoints;
			return this;
		}

		public Builder toCreate() {
			this.isCreated = true;
			return this;
		}

		public Points build() {
			if (isCreated) {
				this.creationDate = new Date();
			} else {
				this.updateDate = new Date();
			}
			return new Points(this);
		}

	}
}