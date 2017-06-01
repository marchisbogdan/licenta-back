package com.onnisoft.wahoo.model.document;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.onnisoft.wahoo.model.document.enums.SportTypeEnum;

/**
 * 
 * Entity that keeps information about supported sports.
 *
 * @author mbozesan
 * @date 30 Sep 2016 - 13:49:58
 *
 */
@Document(collection = "sports")
public class Sport extends Node implements Serializable {

	private static final long serialVersionUID = -7086401561967171913L;

	private String name;
	private SportTypeEnum type;
	private String description;

	public Sport() {
	}

	private Sport(Builder builder) {
		super(builder.id, builder.creationDate, builder.updateDate);
		this.name = builder.name;
		this.type = builder.type;
		this.description = builder.description;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the type
	 */
	public SportTypeEnum getType() {
		return type;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Sport other = (Sport) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	public static final class Builder {
		private String id;
		private String description;
		private String name;
		private SportTypeEnum type;

		private boolean isCreated;

		private Date creationDate;
		private Date updateDate;

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder type(SportTypeEnum type) {
			this.type = type;
			return this;
		}

		public Builder toCreate() {
			this.isCreated = true;
			return this;
		}

		public Sport build() {
			if (isCreated) {
				this.creationDate = new Date();
			} else {
				this.updateDate = new Date();
			}
			return new Sport(this);
		}
	}
}
