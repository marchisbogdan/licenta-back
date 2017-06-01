package com.onnisoft.wahoo.model.document;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "virtualCompetition-groups")
public class VirtualCompetitionGroup extends Node implements Serializable {

	private static final long serialVersionUID = -2726962238634373836L;

	private String name;
	private String password;
	@DBRef
	private VirtualCompetition virtualCompetition;
	@DBRef
	private Subscriber creator;

	public VirtualCompetitionGroup() {
	}

	private VirtualCompetitionGroup(Builder builder) {
		super(builder.id, builder.creationDate, builder.updateDate);
		this.name = builder.name;
		this.password = builder.password;
		this.virtualCompetition = builder.virtualCompetition;
		this.creator = builder.creator;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return the virtualCompetition
	 */
	public VirtualCompetition getVirtualCompetition() {
		return virtualCompetition;
	}

	/**
	 * @return the creator
	 */
	public Subscriber getCreator() {
		return creator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((virtualCompetition == null) ? 0 : virtualCompetition.hashCode());
		result = prime * result + ((creator == null) ? 0 : creator.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VirtualCompetitionGroup other = (VirtualCompetitionGroup) obj;
		if (virtualCompetition == null) {
			if (other.virtualCompetition != null)
				return false;
		} else if (!virtualCompetition.equals(other.virtualCompetition))
			return false;
		if (creator == null) {
			if (other.creator != null)
				return false;
		} else if (!creator.equals(other.creator))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

	public static final class Builder {
		private String id;
		private String name;
		private String password;
		private VirtualCompetition virtualCompetition;
		private Subscriber creator;

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

		public Builder password(String password) {
			this.password = password;
			return this;
		}

		public Builder virtualCompetition(VirtualCompetition virtualCompetition) {
			this.virtualCompetition = virtualCompetition;
			return this;
		}

		public Builder creator(Subscriber creator) {
			this.creator = creator;
			return this;
		}

		public Builder toCreate() {
			this.isCreated = true;
			return this;
		}

		public VirtualCompetitionGroup build() {
			if (isCreated) {
				this.creationDate = new Date();
			} else {
				this.updateDate = new Date();
			}
			return new VirtualCompetitionGroup(this);
		}
	}
}
