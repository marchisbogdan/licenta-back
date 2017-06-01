package com.onnisoft.wahoo.model.document;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@SuppressWarnings("deprecation")
@Document(collection = "logbooks")
public class Logbook extends Node implements Serializable {

	private static final long serialVersionUID = 1449171240430941090L;
	@DBRef
	private Set<FinancialHistory> financialHistories;
	@DBRef
	private Set<StatisticalHistory> statisticalHistories;
	@DBRef
	private Set<MarketingHistory> marketingHistories;

	/**
	 * Used for json serialization/deserialization.
	 */
	public Logbook() {
	}

	/**
	 * @param financialHistories
	 * @param statisticalHistories
	 * @param marketingHistories
	 */
	private Logbook(Builder builder) {
		super(builder.id, builder.creationDate, builder.updateDate);
		this.financialHistories = builder.financialHistories;
		this.statisticalHistories = builder.statisticalHistories;
		this.marketingHistories = builder.marketingHistories;
	}

	/**
	 * @return the financialHistories
	 */
	public Set<FinancialHistory> getFinancialHistories() {
		return this.financialHistories;
	}

	/**
	 * @return the statisticalHistories
	 */
	public Set<StatisticalHistory> getStatisticalHistories() {
		return this.statisticalHistories;
	}

	/**
	 * @return the marketingHistories
	 */
	public Set<MarketingHistory> getMarketingHistories() {
		return this.marketingHistories;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((financialHistories == null) ? 0 : financialHistories.hashCode());
		result = prime * result + ((marketingHistories == null) ? 0 : marketingHistories.hashCode());
		result = prime * result + ((statisticalHistories == null) ? 0 : statisticalHistories.hashCode());
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
		Logbook other = (Logbook) obj;
		if (financialHistories == null) {
			if (other.financialHistories != null)
				return false;
		} else if (!financialHistories.equals(other.financialHistories))
			return false;
		if (marketingHistories == null) {
			if (other.marketingHistories != null)
				return false;
		} else if (!marketingHistories.equals(other.marketingHistories))
			return false;
		if (statisticalHistories == null) {
			if (other.statisticalHistories != null)
				return false;
		} else if (!statisticalHistories.equals(other.statisticalHistories))
			return false;
		return true;
	}

	public static final class Builder {
		private String id;
		private Set<FinancialHistory> financialHistories;
		private Set<StatisticalHistory> statisticalHistories;
		private Set<MarketingHistory> marketingHistories;

		private boolean isCreated;

		private Date creationDate;
		private Date updateDate;

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder financialHistories(Set<FinancialHistory> financialHistories) {
			this.financialHistories = financialHistories;
			return this;
		}

		public Builder statisticalHistories(Set<StatisticalHistory> statisticalHistories) {
			this.statisticalHistories = statisticalHistories;
			return this;
		}

		public Builder marketingHistories(Set<MarketingHistory> marketingHistories) {
			this.marketingHistories = marketingHistories;
			return this;
		}

		public Builder toCreate() {
			this.isCreated = true;
			return this;
		}

		public Logbook build() {
			if (isCreated) {
				this.creationDate = new Date();
			} else {
				this.updateDate = new Date();
			}
			return new Logbook(this);
		}
	}
}
