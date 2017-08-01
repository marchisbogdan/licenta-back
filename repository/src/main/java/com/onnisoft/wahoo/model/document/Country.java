package com.onnisoft.wahoo.model.document;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "countries")
public class Country extends Node implements Serializable {

	private static final long serialVersionUID = 2334755362286781079L;

	@JsonProperty(required = true)
	private String name;
	private String abbreviation;
	private String flagLink;

	/**
	 * Used for serialization/deserialization.
	 */
	public Country() {

	}

	/**
	 * 
	 * @param id
	 * @param name
	 * @param description
	 * @param abbreviation
	 */
	private Country(Builder builder) {
		super(builder.id, builder.creationDate, builder.updateDate);
		this.name = builder.name;
		this.abbreviation = builder.abbreviation;
		this.flagLink = builder.flagLink;
	}

	/**
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return abbreviation
	 */
	public String getAbbreviation() {
		return abbreviation;
	}

	/**
	 * @return the flagLink
	 */
	public String getFlagLink() {
		return flagLink;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((abbreviation == null) ? 0 : abbreviation.hashCode());
		result = prime * result + ((flagLink == null) ? 0 : flagLink.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Country other = (Country) obj;
		if (abbreviation == null) {
			if (other.abbreviation != null)
				return false;
		} else if (!abbreviation.equals(other.abbreviation))
			return false;
		if (flagLink == null) {
			if (other.flagLink != null)
				return false;
		} else if (!flagLink.equals(other.flagLink))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public static final class Builder {
		private String id;
		private String name;
		private String abbreviation;
		private String flagLink;

		private boolean isCreated;

		private Date creationDate;
		private Date updateDate;

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder abbreviation(String abbreviation) {
			this.abbreviation = abbreviation;
			return this;
		}

		public Builder flaglink(String flaglink) {
			this.flagLink = flaglink;
			return this;
		}

		public Builder toCreate() {
			this.isCreated = true;
			return this;
		}

		public Country build() {
			if (isCreated) {
				this.creationDate = new Date();
			} else {
				this.updateDate = new Date();
			}
			return new Country(this);
		}
	}

}
