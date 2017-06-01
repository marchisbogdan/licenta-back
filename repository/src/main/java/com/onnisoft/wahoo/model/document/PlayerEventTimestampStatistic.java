package com.onnisoft.wahoo.model.document;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.onnisoft.wahoo.model.document.enums.EventActionEnum;

/**
 * Model for statistics of each player at a certain time period linked to the
 * event.
 *
 * @author alexandru.mos
 * @date Feb 11, 2017 - 7:04:49 PM
 *
 */
@Document(collection = "player-event-timestamp-statistics")
public final class PlayerEventTimestampStatistic extends Node implements Serializable {

	private static final long serialVersionUID = -9195679646919954230L;

	private EventActionEnum action;
	@DBRef
	private RealCompetitor competitor;
	@DBRef
	private Player player;
	private int actionMinute;
	private int actionSecond;
	private Date lastUpdated;
	@DBRef
	private Event event;

	/**
	 * Used for json serialization/deserialization.
	 */
	public PlayerEventTimestampStatistic() {
	}

	/**
	 * @param builder
	 *            - the builder factory used to initialize the
	 *            {@link PlayerEventTimestampStatistic} attributes.
	 */
	private PlayerEventTimestampStatistic(Builder builder) {
		super(builder.id, builder.creationDate, builder.updateDate);
		this.action = builder.action;
		this.competitor = builder.competitor;
		this.player = builder.player;
		this.actionMinute = builder.actionMinute;
		this.actionSecond = builder.actionSecond;
		this.lastUpdated = builder.lastUpdated;
		this.event = builder.event;
	}

	/**
	 * @return the action
	 */
	public EventActionEnum getAction() {
		return this.action;
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
	 * @return the actionMinute
	 */
	public int getActionMinute() {
		return this.actionMinute;
	}

	/**
	 * @return the actionSecond
	 */
	public int getActionSecond() {
		return this.actionSecond;
	}

	/**
	 * @return the lastUpdated
	 */
	public Date getLastUpdated() {
		return this.lastUpdated;
	}

	/**
	 * @return the event
	 */
	public Event getEvent() {
		return this.event;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + actionMinute;
		result = prime * result + actionSecond;
		result = prime * result + ((competitor == null) ? 0 : competitor.hashCode());
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		result = prime * result + ((lastUpdated == null) ? 0 : lastUpdated.hashCode());
		result = prime * result + ((player == null) ? 0 : player.hashCode());
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
		PlayerEventTimestampStatistic other = (PlayerEventTimestampStatistic) obj;
		if (action != other.action)
			return false;
		if (actionMinute != other.actionMinute)
			return false;
		if (actionSecond != other.actionSecond)
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
		if (lastUpdated == null) {
			if (other.lastUpdated != null)
				return false;
		} else if (!lastUpdated.equals(other.lastUpdated))
			return false;
		if (player == null) {
			if (other.player != null)
				return false;
		} else if (!player.equals(other.player))
			return false;
		return true;
	}

	public static final class Builder {

		private String id;

		private EventActionEnum action;
		private RealCompetitor competitor;
		private Player player;
		private int actionMinute;
		private int actionSecond;
		private Date lastUpdated;
		private Event event;

		private boolean isCreated;

		private Date creationDate;
		private Date updateDate;

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder action(EventActionEnum action) {
			this.action = action;
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

		public Builder actionMinute(int actionMinute) {
			this.actionMinute = actionMinute;
			return this;
		}

		public Builder actionSecond(int actionSecond) {
			this.actionSecond = actionSecond;
			return this;
		}

		public Builder lastUpdated(Date lastUpdated) {
			this.lastUpdated = lastUpdated;
			return this;
		}

		public Builder event(Event event) {
			this.event = event;
			return this;
		}

		public Builder toCreate() {
			this.isCreated = true;
			return this;
		}

		public PlayerEventTimestampStatistic build() {
			if (isCreated) {
				this.creationDate = new Date();
			} else {
				this.updateDate = new Date();
			}
			return new PlayerEventTimestampStatistic(this);
		}
	}
}
