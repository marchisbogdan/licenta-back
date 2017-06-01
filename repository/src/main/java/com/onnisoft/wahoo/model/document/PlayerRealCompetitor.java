package com.onnisoft.wahoo.model.document;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.onnisoft.wahoo.model.document.enums.PlayerPositionEnum;

@Document(collection = "players-real-competitors")
public class PlayerRealCompetitor extends Node implements Serializable {

	private static final long serialVersionUID = 1830763527662657831L;

	@DBRef
	private RealCompetitor competitor;
	@DBRef
	private Player player;
	private Boolean captain;
	private Round round;
	private Map<String, PlayerPositionEnum> position;

	public PlayerRealCompetitor() {
	}

	private PlayerRealCompetitor(Builder builder) {
		super(builder.id, builder.creationDate, builder.updateDate);
		this.competitor = builder.competitor;
		this.player = builder.player;
		this.captain = builder.captain;
		this.round = builder.round;
		this.position = builder.position;
	}

	/**
	 * @return the competitor
	 */
	public RealCompetitor getCompetitor() {
		return competitor;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @return the captain
	 */
	public Boolean isCaptain() {
		return captain;
	}

	/**
	 * @return the round
	 */
	public Round getRound() {
		return round;
	}

	/**
	 * @return the position
	 */
	public Map<String, PlayerPositionEnum> getPosition() {
		return position;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((captain == null) ? 0 : captain.hashCode());
		result = prime * result + ((competitor == null) ? 0 : competitor.hashCode());
		result = prime * result + ((player == null) ? 0 : player.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((round == null) ? 0 : round.hashCode());
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
		PlayerRealCompetitor other = (PlayerRealCompetitor) obj;
		if (captain == null) {
			if (other.captain != null)
				return false;
		} else if (!captain.equals(other.captain))
			return false;
		if (competitor == null) {
			if (other.competitor != null)
				return false;
		} else if (!competitor.equals(other.competitor))
			return false;
		if (player == null) {
			if (other.player != null)
				return false;
		} else if (!player.equals(other.player))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (round == null) {
			if (other.round != null)
				return false;
		} else if (!round.equals(other.round))
			return false;
		return true;
	}

	public static final class Builder {
		private String id;
		private RealCompetitor competitor;
		private Player player;
		private Boolean captain;
		private Round round;
		private Map<String, PlayerPositionEnum> position;

		private boolean isCreated;

		private Date creationDate;
		private Date updateDate;

		public Builder id(String id) {
			this.id = id;
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

		public Builder captain(Boolean captain) {
			this.captain = captain;
			return this;
		}

		public Builder round(Round round) {
			this.round = round;
			return this;
		}

		public Builder position(Map<String, PlayerPositionEnum> position) {
			this.position = position;
			return this;
		}

		public Builder toCreate() {
			this.isCreated = true;
			return this;
		}

		public PlayerRealCompetitor build() {
			if (isCreated) {
				this.creationDate = new Date();
			} else {
				this.updateDate = new Date();
			}
			return new PlayerRealCompetitor(this);
		}
	}
}
