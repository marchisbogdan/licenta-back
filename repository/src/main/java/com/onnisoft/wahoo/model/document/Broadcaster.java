package com.onnisoft.wahoo.model.document;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "broadcasters")
public class Broadcaster extends Node implements Serializable {

	private static final long serialVersionUID = 968000244304962644L;

	private String name;

	@DBRef
	private Country country;

	/**
	 * Used for json serialization/deserialization.
	 */
	public Broadcaster() {
	}

	/**
	 * @param id
	 * @param name
	 * @param country
	 * @param event
	 */
	public Broadcaster(Builder builder) {
		super(builder.id, builder.creationDate, builder.updateDate);
		this.name = builder.name;
		this.country = builder.country;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return the country
	 */
	public Country getCountry() {
		return this.country;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((country == null) ? 0 : country.hashCode());
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
		Broadcaster other = (Broadcaster) obj;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
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
		private Country country;

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

		public Builder country(Country country) {
			this.country = country;
			return this;
		}

		public Builder toCreate() {
			this.isCreated = true;
			return this;
		}

		public Broadcaster build() {
			if (isCreated) {
				this.creationDate = new Date();
			} else {
				this.updateDate = new Date();
			}
			return new Broadcaster(this);
		}
	}
}
