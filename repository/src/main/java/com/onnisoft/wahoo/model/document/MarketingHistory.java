package com.onnisoft.wahoo.model.document;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "marketing-histories")
public class MarketingHistory extends Node implements Serializable {

	private static final long serialVersionUID = 6592307483875260148L;

	@DBRef
	private CommunityEngagement communityEngagement;

	/**
	 * Used for json serialization/deserialization.
	 */
	public MarketingHistory() {
	}

	/**
	 * @param id
	 * @param communityEngagement
	 */
	private MarketingHistory(Builder builder) {
		super(builder.id, builder.creationDate, builder.updateDate);
		this.communityEngagement = builder.communityEngagement;
	}

	/**
	 * @return the communityEngagement
	 */
	public CommunityEngagement getCommunityEngagement() {
		return this.communityEngagement;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((communityEngagement == null) ? 0 : communityEngagement.hashCode());
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
		MarketingHistory other = (MarketingHistory) obj;
		if (communityEngagement == null) {
			if (other.communityEngagement != null)
				return false;
		} else if (!communityEngagement.equals(other.communityEngagement))
			return false;
		return true;
	}

	public static final class Builder {
		private String id;
		private CommunityEngagement communityEngagement;

		private boolean isCreated;

		private Date creationDate;
		private Date updateDate;

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder communityEngagement(CommunityEngagement communityEngagement) {
			this.communityEngagement = communityEngagement;
			return this;
		}

		public Builder toCreate() {
			this.isCreated = true;
			return this;
		}

		public MarketingHistory build() {
			if (isCreated) {
				this.creationDate = new Date();
			} else {
				this.updateDate = new Date();
			}
			return new MarketingHistory(this);
		}
	}

}
