package com.onnisoft.wahoo.model.document;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entity that keeps information about the competitors. A competitor can be a
 * user team or an athlete.
 *
 * @author mbozesan
 * @date 23 Sep 2016 - 16:42:06
 */
@Document(collection = "virtual-competitors")
public class VirtualCompetitor extends Node implements Serializable {

	private static final long serialVersionUID = 3919883261785189036L;

	private String name;
	@DBRef
	private Subscriber subscriber;
	@DBRef
	private VirtualCompetition competition;
	@DBRef
	private VirtualCompetitionGroup group;
	private Integer rank;
	private Integer value;
	private Integer totalPoints;

	public VirtualCompetitor() {
	}

	private VirtualCompetitor(Builder builder) {
		super(builder.id, builder.creationDate, builder.updateDate);
		this.name = builder.name;
		this.subscriber = builder.subscriber;
		this.competition = builder.competition;
		this.group = builder.group;
		this.rank = builder.rank;
		this.value = builder.value;
		this.totalPoints = builder.totalPoints;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the userId
	 */
	public Subscriber getSubscriber() {
		return subscriber;
	}

	/**
	 * @return the competition
	 */
	public VirtualCompetition getCompetition() {
		return competition;
	}

	/**
	 * @return the group
	 */
	public VirtualCompetitionGroup getGroup() {
		return group;
	}

	/**
	 * @return the rank
	 */
	public Integer getRank() {
		return rank;
	}

	/**
	 * @return the value
	 */
	public Integer getValue() {
		return value;
	}

	/**
	 * @return the totalPoints
	 */
	public Integer getTotalPoints() {
		return totalPoints;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((competition == null) ? 0 : competition.hashCode());
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((rank == null) ? 0 : rank.hashCode());
		result = prime * result + ((subscriber == null) ? 0 : subscriber.hashCode());
		result = prime * result + ((totalPoints == null) ? 0 : totalPoints.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		VirtualCompetitor other = (VirtualCompetitor) obj;
		if (competition == null) {
			if (other.competition != null)
				return false;
		} else if (!competition.equals(other.competition))
			return false;
		if (group == null) {
			if (other.group != null)
				return false;
		} else if (!group.equals(other.group))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (rank == null) {
			if (other.rank != null)
				return false;
		} else if (!rank.equals(other.rank))
			return false;
		if (subscriber == null) {
			if (other.subscriber != null)
				return false;
		} else if (!subscriber.equals(other.subscriber))
			return false;
		if (totalPoints == null) {
			if (other.totalPoints != null)
				return false;
		} else if (!totalPoints.equals(other.totalPoints))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	public static final class Builder {
		private String id;
		private String name;
		private Subscriber subscriber;
		private VirtualCompetition competition;
		private VirtualCompetitionGroup group;
		private Integer rank;
		private Integer value;
		private Integer totalPoints;

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

		public Builder subscriber(Subscriber subscriber) {
			this.subscriber = subscriber;
			return this;
		}

		public Builder virtualCompetition(VirtualCompetition virtualCompetition) {
			this.competition = virtualCompetition;
			return this;
		}

		public Builder group(VirtualCompetitionGroup virtualCompetitionGroup) {
			this.group = virtualCompetitionGroup;
			return this;
		}

		public Builder rank(Integer rank) {
			this.rank = rank;
			return this;
		}

		public Builder value(Integer value) {
			this.value = value;
			return this;
		}

		public Builder totalPoints(Integer totalPoints) {
			this.totalPoints = totalPoints;
			return this;
		}

		public Builder toCreate() {
			this.isCreated = true;
			return this;
		}

		public VirtualCompetitor build() {
			if (isCreated) {
				this.creationDate = new Date();
			} else {
				this.updateDate = new Date();
			}
			return new VirtualCompetitor(this);
		}
	}

}
