package com.onnisoft.wahoo.model.document;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "financial-histories")
public class FinancialHistory extends Node implements Serializable {

	private static final long serialVersionUID = 3379632075281805102L;
	private Double totalGateReceipt;
	private Double prizeMoney;

	@DBRef
	private Wage wage;
	@DBRef
	private Set<Sponsorship> sponsorships;

	/**
	 * Used for json serialization/deserialization.
	 */
	public FinancialHistory() {
	}

	/**
	 * @param id
	 * @param startTime
	 * @param endTime
	 * @param totalGateReceipt
	 * @param prizeMoney
	 * @param wage
	 * @param sponsorships
	 */
	private FinancialHistory(Builder builder) {
		super(builder.id, builder.creationDate, builder.updateDate);
		this.totalGateReceipt = builder.totalGateReceipt;
		this.prizeMoney = builder.prizeMoney;
		this.wage = builder.wage;
		this.sponsorships = builder.sponsorships;
	}

	/**
	 * @return the totalGateReceipt
	 */
	public Double getTotalGateReceipt() {
		return this.totalGateReceipt;
	}

	/**
	 * @return the prizeMoney
	 */
	public Double getPrizeMoney() {
		return this.prizeMoney;
	}

	/**
	 * @return the wage
	 */
	public Wage getWage() {
		return this.wage;
	}

	/**
	 * @return the sponsorships
	 */
	public Set<Sponsorship> getSponsorships() {
		return this.sponsorships;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((prizeMoney == null) ? 0 : prizeMoney.hashCode());
		result = prime * result + ((sponsorships == null) ? 0 : sponsorships.hashCode());
		result = prime * result + ((totalGateReceipt == null) ? 0 : totalGateReceipt.hashCode());
		result = prime * result + ((wage == null) ? 0 : wage.hashCode());
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
		FinancialHistory other = (FinancialHistory) obj;
		if (prizeMoney == null) {
			if (other.prizeMoney != null)
				return false;
		} else if (!prizeMoney.equals(other.prizeMoney))
			return false;
		if (sponsorships == null) {
			if (other.sponsorships != null)
				return false;
		} else if (!sponsorships.equals(other.sponsorships))
			return false;
		if (totalGateReceipt == null) {
			if (other.totalGateReceipt != null)
				return false;
		} else if (!totalGateReceipt.equals(other.totalGateReceipt))
			return false;
		if (wage == null) {
			if (other.wage != null)
				return false;
		} else if (!wage.equals(other.wage))
			return false;
		return true;
	}

	public static final class Builder {
		private String id;
		private Double totalGateReceipt;
		private Double prizeMoney;
		private Wage wage;
		private Set<Sponsorship> sponsorships;

		private boolean isCreated;

		private Date creationDate;
		private Date updateDate;

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder totalGateReceipt(Double totalGateReceipt) {
			this.totalGateReceipt = totalGateReceipt;
			return this;
		}

		public Builder prizeMoney(Double prizeMoney) {
			this.prizeMoney = prizeMoney;
			return this;
		}

		public Builder wage(Wage wage) {
			this.wage = wage;
			return this;
		}

		public Builder sponsorships(Set<Sponsorship> sponsorships) {
			this.sponsorships = sponsorships;
			return this;
		}

		public Builder toCreate() {
			this.isCreated = true;
			return this;
		}

		public FinancialHistory build() {
			if (isCreated) {
				this.creationDate = new Date();
			} else {
				this.updateDate = new Date();
			}
			return new FinancialHistory(this);
		}
	}
}
