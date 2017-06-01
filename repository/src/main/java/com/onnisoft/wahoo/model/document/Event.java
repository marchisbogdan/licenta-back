package com.onnisoft.wahoo.model.document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.onnisoft.wahoo.model.document.enums.EventStatusEnum;

/**
 * 
 * Abstract class that serves as base type for the different types of games.
 *
 * @author mbozesan
 * @date 30 Sep 2016 - 13:22:09
 *
 */
@Document(collection = "event")
public class Event extends Node implements Serializable {

	private static final long serialVersionUID = 3616474077528233788L;

	private String name;
	private Date startDateTime;
	private String stadium;
	private EventStatusEnum status;
	@DBRef
	private Round round;
	private String description;

	private EventStatus eventStatus;
	@DBRef
	private Venue venue;

	@DBRef
	private List<EventCompetitor> eventCompetitors;

	@DBRef
	private List<Broadcaster> broadcasters;

	public Event() {
	}

	private Event(Builder builder) {
		super(builder.id, builder.creationDate, builder.updateDate);
		this.name = builder.name;
		this.startDateTime = builder.startDateTime;
		this.stadium = builder.stadium;
		this.status = builder.status;
		this.round = builder.round;
		this.description = builder.description;
		this.eventStatus = builder.eventStatus;
		this.venue = builder.venue;
		this.eventCompetitors = builder.eventCompetitors;
		this.broadcasters = builder.broadcasters;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the dateTime
	 */
	public Date getStartDateTime() {
		return startDateTime;
	}

	/**
	 * @return the stadium
	 */
	public String getStadium() {
		return stadium;
	}

	/**
	 * @return the status
	 */
	public EventStatusEnum getStatus() {
		return status;
	}

	/**
	 * @return the round
	 */
	public Round getRound() {
		return round;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 
	 * @return the event status
	 */
	public EventStatus getEventStatus() {
		return eventStatus;
	}

	public Venue getVenue() {
		return venue;
	}

	public List<EventCompetitor> getEventCompetitors() {
		return eventCompetitors;
	}

	public List<Broadcaster> getBroadcasters() {
		return broadcasters;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((broadcasters == null) ? 0 : broadcasters.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((eventCompetitors == null) ? 0 : eventCompetitors.hashCode());
		result = prime * result + ((eventStatus == null) ? 0 : eventStatus.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((round == null) ? 0 : round.hashCode());
		result = prime * result + ((stadium == null) ? 0 : stadium.hashCode());
		result = prime * result + ((startDateTime == null) ? 0 : startDateTime.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((venue == null) ? 0 : venue.hashCode());
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
		Event other = (Event) obj;
		if (broadcasters == null) {
			if (other.broadcasters != null)
				return false;
		} else if (!broadcasters.equals(other.broadcasters))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (eventCompetitors == null) {
			if (other.eventCompetitors != null)
				return false;
		} else if (!eventCompetitors.equals(other.eventCompetitors))
			return false;
		if (eventStatus == null) {
			if (other.eventStatus != null)
				return false;
		} else if (!eventStatus.equals(other.eventStatus))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (round == null) {
			if (other.round != null)
				return false;
		} else if (!round.equals(other.round))
			return false;
		if (stadium == null) {
			if (other.stadium != null)
				return false;
		} else if (!stadium.equals(other.stadium))
			return false;
		if (startDateTime == null) {
			if (other.startDateTime != null)
				return false;
		} else if (!startDateTime.equals(other.startDateTime))
			return false;
		if (status != other.status)
			return false;
		if (venue == null) {
			if (other.venue != null)
				return false;
		} else if (!venue.equals(other.venue))
			return false;
		return true;
	}

	public static final class Builder {

		private String id;
		private String name;
		private Date startDateTime;
		private String stadium;
		private EventStatusEnum status;
		private Round round;
		private String description;
		private EventStatus eventStatus;
		private Venue venue;
		private List<EventCompetitor> eventCompetitors;
		private List<Broadcaster> broadcasters;

		private Date creationDate;
		private Date updateDate;

		private boolean isCreated;

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder startDateTime(Date startDateTime) {
			this.startDateTime = startDateTime;
			return this;
		}

		public Builder stadium(String stadium) {
			this.stadium = stadium;
			return this;
		}

		public Builder status(EventStatusEnum status) {
			this.status = status;
			return this;
		}

		public Builder round(Round round) {
			this.round = round;
			return this;
		}

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public Builder eventStatus(EventStatus eventStatus) {
			this.eventStatus = eventStatus;
			return this;
		}

		public Builder venue(Venue venue) {
			this.venue = venue;
			return this;
		}

		public Builder eventCompetitors(List<EventCompetitor> eventCompetitors) {
			this.eventCompetitors = eventCompetitors;
			return this;
		}

		public Builder broadcasters(List<Broadcaster> broadcasters) {
			this.broadcasters = broadcasters;
			return this;
		}

		public Builder toCreate() {
			this.isCreated = true;
			return this;
		}

		public Event build() {
			if (isCreated) {
				this.creationDate = new Date();
			} else {
				this.updateDate = new Date();
			}
			return new Event(this);
		}
	}
}