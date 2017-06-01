package com.onnisoft.wahoo.model.document;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
 * Entity that keeps information about the earnings. A user will withdraw the
 * earnings from a competition which offers real money as prizes.
 *
 * @author mbozesan
 * @date 23 Sep 2016 - 16:42:33
 *
 */
@Document(collection = "earnings")
public class Earning extends Node implements Serializable {

	private static final long serialVersionUID = 4371186147470013521L;

	@DBRef
	private Subscriber subscriber;
	private Double balance;
	private Double toWithdraw;

	public Earning() {
	}

	private Earning(Builder builder) {
		super(builder.id, builder.creationDate, builder.updateDate);
		this.subscriber = builder.subscriber;
		this.balance = builder.balance;
		this.toWithdraw = builder.toWithdraw;
	}

	/**
	 * @return the userId
	 */
	public Subscriber getSubscriber() {
		return subscriber;
	}

	/**
	 * @return the balance
	 */
	public Double getBalance() {
		return balance;
	}

	/**
	 * @return the toWithdraw
	 */
	public Double getToWithdraw() {
		return toWithdraw;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((balance == null) ? 0 : balance.hashCode());
		result = prime * result + ((subscriber == null) ? 0 : subscriber.hashCode());
		result = prime * result + ((toWithdraw == null) ? 0 : toWithdraw.hashCode());
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
		Earning other = (Earning) obj;
		if (balance == null) {
			if (other.balance != null)
				return false;
		} else if (!balance.equals(other.balance))
			return false;
		if (subscriber == null) {
			if (other.subscriber != null)
				return false;
		} else if (!subscriber.equals(other.subscriber))
			return false;
		if (toWithdraw == null) {
			if (other.toWithdraw != null)
				return false;
		} else if (!toWithdraw.equals(other.toWithdraw))
			return false;
		return true;
	}

	public static final class Builder {
		private String id;
		private Subscriber subscriber;
		private Double balance;
		private Double toWithdraw;

		private boolean isCreated;

		private Date creationDate;
		private Date updateDate;

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder subscriber(Subscriber subscriber) {
			this.subscriber = subscriber;
			return this;
		}

		public Builder balance(Double balance) {
			this.balance = balance;
			return this;
		}

		public Builder toWithdraw(Double toWithdraw) {
			this.toWithdraw = toWithdraw;
			return this;
		}

		public Builder toCreate() {
			this.isCreated = true;
			return this;
		}

		public Earning build() {
			if (isCreated) {
				this.creationDate = new Date();
			} else {
				this.updateDate = new Date();
			}
			return new Earning(this);
		}
	}
}
