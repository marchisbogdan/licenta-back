package com.onnisoft.wahoo.model.document;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.onnisoft.wahoo.model.document.enums.CurrencyEnum;

@Document(collection = "wages")
public class Wage extends Node implements Serializable {

	private static final long serialVersionUID = -5400930454260109896L;
	private Double netSalary;
	private Double sponsorSalary;
	private Double totalBonus;
	private CurrencyEnum currency;

	/**
	 * Used for json serialization/deserialization.
	 */
	public Wage() {
	}

	/**
	 * @param id
	 * @param netSalary
	 * @param sponsorSalary
	 * @param totalBonus
	 * @param currency
	 */
	private Wage(Builder builder) {
		super(builder.id, builder.creationDate, builder.updateDate);
		this.netSalary = builder.netSalary;
		this.sponsorSalary = builder.sponsorSalary;
		this.totalBonus = builder.totalBonus;
		this.currency = builder.currency;
	}

	/**
	 * @return the netSalary
	 */
	public Double getNetSalary() {
		return this.netSalary;
	}

	/**
	 * @return the sponsorSalary
	 */
	public Double getSponsorSalary() {
		return this.sponsorSalary;
	}

	/**
	 * @return the totalBonus
	 */
	public Double getTotalBonus() {
		return this.totalBonus;
	}

	/**
	 * @return the currency
	 */
	public CurrencyEnum getCurrency() {
		return this.currency;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((netSalary == null) ? 0 : netSalary.hashCode());
		result = prime * result + ((sponsorSalary == null) ? 0 : sponsorSalary.hashCode());
		result = prime * result + ((totalBonus == null) ? 0 : totalBonus.hashCode());
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
		Wage other = (Wage) obj;
		if (currency != other.currency)
			return false;
		if (netSalary == null) {
			if (other.netSalary != null)
				return false;
		} else if (!netSalary.equals(other.netSalary))
			return false;
		if (sponsorSalary == null) {
			if (other.sponsorSalary != null)
				return false;
		} else if (!sponsorSalary.equals(other.sponsorSalary))
			return false;
		if (totalBonus == null) {
			if (other.totalBonus != null)
				return false;
		} else if (!totalBonus.equals(other.totalBonus))
			return false;
		return true;
	}

	public static final class Builder {
		private String id;
		private Double netSalary;
		private Double sponsorSalary;
		private Double totalBonus;
		private CurrencyEnum currency;

		private boolean isCreated;

		private Date creationDate;
		private Date updateDate;

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder netSalary(Double netSalary) {
			this.netSalary = netSalary;
			return this;
		}

		public Builder sponsorSalary(Double sponsorSalary) {
			this.sponsorSalary = sponsorSalary;
			return this;
		}

		public Builder totalBonus(Double totalBonus) {
			this.totalBonus = totalBonus;
			return this;
		}

		public Builder currency(CurrencyEnum currency) {
			this.currency = currency;
			return this;
		}

		public Builder toCreate() {
			this.isCreated = true;
			return this;
		}

		public Wage build() {
			if (isCreated) {
				this.creationDate = new Date();
			} else {
				this.updateDate = new Date();
			}
			return new Wage(this);
		}
	}
}
