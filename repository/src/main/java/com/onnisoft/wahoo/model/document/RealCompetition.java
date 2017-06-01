package com.onnisoft.wahoo.model.document;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
 * Entity that keeps information about leagues.
 *
 * @author mbozesan
 * @date 30 Sep 2016 - 13:23:07
 *
 */
@Document(collection = "real-competition")
public class RealCompetition extends Node implements Serializable {

	private static final long serialVersionUID = 4772508086003228636L;

	private String name;
	private String logoLink;
	@DBRef
	private Country country;
	@DBRef
	private Sport sport;

	public RealCompetition() {
	}

	private RealCompetition(Builder builder) {
		super(builder.id, builder.creationDate, builder.updateDate);
		this.name = builder.name;
		this.logoLink = builder.logoLink;
		this.country = builder.country;
		this.sport = builder.sport;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the logoLink
	 */
	public String getLogoLink() {
		return logoLink;
	}

	/**
	 * @return the country
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * @return the sport
	 */
	public Sport getSport() {
		return sport;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((logoLink == null) ? 0 : logoLink.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((sport == null) ? 0 : sport.hashCode());
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
		RealCompetition other = (RealCompetition) obj;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (logoLink == null) {
			if (other.logoLink != null)
				return false;
		} else if (!logoLink.equals(other.logoLink))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (sport == null) {
			if (other.sport != null)
				return false;
		} else if (!sport.equals(other.sport))
			return false;
		return true;
	}

	public static final class Builder {
		private String id;
		private String name;
		private String logoLink;
		private Country country;
		private Sport sport;

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

		public Builder logoLink(String logoLink) {
			this.logoLink = logoLink;
			return this;
		}

		public Builder country(Country country) {
			this.country = country;
			return this;
		}

		public Builder sport(Sport sport) {
			this.sport = sport;
			return this;
		}

		public Builder toCreate() {
			this.isCreated = true;
			return this;
		}

		public RealCompetition build() {
			if (isCreated) {
				this.creationDate = new Date();
			} else {
				this.updateDate = new Date();
			}
			return new RealCompetition(this);
		}
	}
}
