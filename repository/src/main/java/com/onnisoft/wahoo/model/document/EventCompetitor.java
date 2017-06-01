package com.onnisoft.wahoo.model.document;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "event-competitor")
public class EventCompetitor extends Node implements Serializable {

	private static final long serialVersionUID = 7541012933466457839L;

	@DBRef
	private RealCompetitor competitor;
	@DBRef
	private Event event;
	private Integer position;

	public EventCompetitor() {
	}

	private EventCompetitor(Builder builder) {
		super(builder.id, builder.creationDate, builder.updateDate);
		this.competitor = builder.competitor;
		this.event = builder.event;
		this.position = builder.position;
	}

	/**
	 * @return the competitor
	 */
	public RealCompetitor getCompetitor() {
		return competitor;
	}

	/**
	 * @return the event
	 */
	public Event getEvent() {
		return event;
	}

	/**
	 * @return the position
	 */
	public Integer getPosition() {
		return position;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((competitor == null) ? 0 : competitor.hashCode());
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
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
		EventCompetitor other = (EventCompetitor) obj;
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
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		return true;
	}

	public static final class Builder {
		private String id;
		private RealCompetitor competitor;
		private Event event;
		private Integer position;

		private boolean isCreated;

		private Date creationDate;
		private Date updateDate;

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder competitior(RealCompetitor competitor) {
			this.competitor = competitor;
			return this;
		}

		public Builder event(Event event) {
			this.event = event;
			return this;
		}

		public Builder position(int position) {
			this.position = position;
			return this;
		}

		public Builder toCrate() {
			this.isCreated = true;
			return this;
		}

		public EventCompetitor build() {
			if (isCreated) {
				this.creationDate = new Date();
			} else {
				this.updateDate = new Date();
			}
			return new EventCompetitor(this);
		}
	}
}
