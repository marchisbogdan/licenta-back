package com.onnisoft.wahoo.model.document;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Deprecated
@Document(collection = "statistical_histories")
public class StatisticalHistory extends Node implements Serializable {

	private static final long serialVersionUID = -4023248377821543607L;

	@DBRef
	private Set<PerformanceStatistic> performanceStatistics;

	/**
	 * Used for json serialization/deserialization.
	 */
	public StatisticalHistory() {
	}

	/**
	 * @param id
	 * @param performanceStatistics
	 */
	private StatisticalHistory(Builder builder) {
		super(builder.id, builder.creationDate, builder.updateDate);
		this.performanceStatistics = builder.performanceStatistics;
	}

	/**
	 * @return the performanceStatistics
	 */
	public Set<PerformanceStatistic> getPerformanceStatistics() {
		return this.performanceStatistics;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((performanceStatistics == null) ? 0 : performanceStatistics.hashCode());
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
		StatisticalHistory other = (StatisticalHistory) obj;
		if (performanceStatistics == null) {
			if (other.performanceStatistics != null)
				return false;
		} else if (!performanceStatistics.equals(other.performanceStatistics))
			return false;
		return true;
	}

	public static final class Builder {
		private String id;
		private Set<PerformanceStatistic> performanceStatistics;

		private boolean isCreated;

		private Date creationDate;
		private Date updateDate;

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder performanceStatistics(Set<PerformanceStatistic> performanceStatistics) {
			this.performanceStatistics = performanceStatistics;
			return this;
		}

		public Builder toCreate() {
			this.isCreated = true;
			return this;
		}

		public StatisticalHistory build() {
			if (isCreated) {
				this.creationDate = new Date();
			} else {
				this.updateDate = new Date();
			}
			return new StatisticalHistory(this);
		}
	}
}
