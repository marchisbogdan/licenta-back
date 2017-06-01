package com.onnisoft.wahoo.model.document;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "venues")
public class Venue extends Node implements Serializable {

	private static final long serialVersionUID = 7336185694346717043L;

	private String name;

	/**
	 * Used for json serialization/deserialization.
	 */
	public Venue() {
	}

	/**
	 * @param builder
	 *            - the builder factory used to initialize the {@link Venue}
	 *            attributes.
	 */
	private Venue(VenueBuilder builder) {
		super(builder.id, builder.creationDate, builder.updateDate);
		this.name = builder.name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
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
		Venue other = (Venue) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public static final class VenueBuilder {

		private String id;
		private String name;

		private boolean isCreated;

		private Date creationDate;
		private Date updateDate;

		public VenueBuilder id(String id) {
			this.id = id;
			return this;
		}

		public VenueBuilder name(String name) {
			this.name = name;
			return this;
		}

		public VenueBuilder toCreate() {
			this.isCreated = true;
			return this;
		}

		public Venue build() {
			if (isCreated) {
				this.creationDate = new Date();
			} else {
				this.updateDate = new Date();
			}
			return new Venue(this);
		}
	}
}
