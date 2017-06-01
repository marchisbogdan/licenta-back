package com.onnisoft.wahoo.model.document;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.onnisoft.wahoo.model.document.enums.SponsorshipTypeEnum;

@Document(collection = "sponsorships")
public class Sponsorship extends Node implements Serializable {

	private static final long serialVersionUID = 4417907893413979445L;
	private Double cost;
	private SponsorshipTypeEnum type;

	@DBRef
	private Brand brand;

	/**
	 * Used for json serialization/deserialization.
	 */
	public Sponsorship() {
	}

	/**
	 * @param id
	 * @param cost
	 * @param type
	 * @param brand
	 */
	private Sponsorship(Builder builder) {
		super(builder.id, builder.creationDate, builder.updateDate);
		this.cost = builder.cost;
		this.type = builder.type;
		this.brand = builder.brand;
	}

	/**
	 * @return the cost
	 */
	public Double getCost() {
		return this.cost;
	}

	/**
	 * @return the type
	 */
	public SponsorshipTypeEnum getType() {
		return this.type;
	}

	/**
	 * @return the brand
	 */
	public Brand getBrand() {
		return this.brand;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((brand == null) ? 0 : brand.hashCode());
		result = prime * result + ((cost == null) ? 0 : cost.hashCode());
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
		Sponsorship other = (Sponsorship) obj;
		if (brand == null) {
			if (other.brand != null)
				return false;
		} else if (!brand.equals(other.brand))
			return false;
		if (cost == null) {
			if (other.cost != null)
				return false;
		} else if (!cost.equals(other.cost))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	public static final class Builder {
		private String id;
		private Double cost;
		private SponsorshipTypeEnum type;
		private Brand brand;

		private boolean isCreated;

		private Date creationDate;
		private Date updateDate;

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder cost(Double cost) {
			this.cost = cost;
			return this;
		}

		public Builder type(SponsorshipTypeEnum type) {
			this.type = type;
			return this;
		}

		public Builder brand(Brand brand) {
			this.brand = brand;
			return this;
		}

		public Builder toCreate() {
			this.isCreated = true;
			return this;
		}

		public Sponsorship build() {
			if (isCreated) {
				this.creationDate = new Date();
			} else {
				this.updateDate = new Date();
			}
			return new Sponsorship(this);
		}
	}
}
