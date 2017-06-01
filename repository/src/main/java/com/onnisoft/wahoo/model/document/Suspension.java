package com.onnisoft.wahoo.model.document;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
 * Entity that keeps information about player suspensions. A suspension can be
 * for medical or legal reasons or because a rule of the competition prevents
 * the player to play a number of matches(a red card for example).
 *
 * @author mbozesan
 * @date 30 Sep 2016 - 13:50:43
 *
 */
@Document(collection = "suspensions")
public class Suspension extends Node implements Serializable {

	private static final long serialVersionUID = 1001818816087773405L;

	private Date startDate;
	private Date returnDate;
	private String reason;

	public Suspension() {
	}

	private Suspension(Builder builder) {
		super(builder.id, builder.creationDate, builder.updateDate);
		this.startDate = builder.startDate;
		this.returnDate = builder.returnDate;
		this.reason = builder.reason;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @return the returnDate
	 */
	public Date getReturnDate() {
		return returnDate;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
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
		result = prime * result + ((reason == null) ? 0 : reason.hashCode());
		result = prime * result + ((returnDate == null) ? 0 : returnDate.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
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
		Suspension other = (Suspension) obj;
		if (reason == null) {
			if (other.reason != null)
				return false;
		} else if (!reason.equals(other.reason))
			return false;
		if (returnDate == null) {
			if (other.returnDate != null)
				return false;
		} else if (!returnDate.equals(other.returnDate))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}

	public static final class Builder {
		private String id;
		private Date startDate;
		private Date returnDate;
		private String reason;

		private Date creationDate;
		private Date updateDate;

		private boolean isCreated;

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder startDate(Date startDate) {
			this.startDate = startDate;
			return this;
		}

		public Builder returnDate(Date returnDate) {
			this.returnDate = returnDate;
			return this;
		}

		public Builder reason(String reason) {
			this.reason = reason;
			return this;
		}

		public Builder toCreate() {
			this.isCreated = true;
			return this;
		}

		public Suspension build() {
			if (isCreated) {
				this.creationDate = new Date();
			} else {
				this.updateDate = new Date();
			}
			return new Suspension(this);
		}

	}
}
