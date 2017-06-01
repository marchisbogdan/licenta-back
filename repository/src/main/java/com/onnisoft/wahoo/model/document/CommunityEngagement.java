package com.onnisoft.wahoo.model.document;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.onnisoft.wahoo.model.document.enums.CommunityEngagementTypeEnum;

@Document(collection = "community_engagements")
public class CommunityEngagement extends Node implements Serializable {

	private static final long serialVersionUID = 7396409373346940939L;
	private String url;
	private int numberOfSubscribers;
	private String language;
	private CommunityEngagementTypeEnum type;

	/**
	 * Used for json serialization/deserialization.
	 */
	public CommunityEngagement() {
	}

	/**
	 * @param id
	 * @param startTime
	 * @param endTime
	 * @param id2
	 * @param url
	 * @param numberOfSubscribers
	 * @param language
	 * @param type
	 */
	private CommunityEngagement(Builder builder) {
		super(builder.id, builder.creationDate, builder.updateDate);
		this.url = builder.url;
		this.numberOfSubscribers = builder.numberOfSubscribers;
		this.language = builder.language;
		this.type = builder.type;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * @return the numberOfSubscribers
	 */
	public int getNumberOfSubscribers() {
		return this.numberOfSubscribers;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return this.language;
	}

	/**
	 * @return the type
	 */
	public CommunityEngagementTypeEnum getType() {
		return this.type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.language == null) ? 0 : this.language.hashCode());
		result = prime * result + this.numberOfSubscribers;
		result = prime * result + ((this.type == null) ? 0 : this.type.hashCode());
		result = prime * result + ((this.url == null) ? 0 : this.url.hashCode());
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
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommunityEngagement other = (CommunityEngagement) obj;
		if (this.language == null) {
			if (other.language != null)
				return false;
		} else if (!this.language.equals(other.language))
			return false;
		if (this.numberOfSubscribers != other.numberOfSubscribers)
			return false;
		if (this.type != other.type)
			return false;
		if (this.url == null) {
			if (other.url != null)
				return false;
		} else if (!this.url.equals(other.url))
			return false;
		return true;
	}

	public static final class Builder {
		private String id;
		private String url;
		private int numberOfSubscribers;
		private String language;
		private CommunityEngagementTypeEnum type;

		private boolean isCreated;

		private Date creationDate;
		private Date updateDate;

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder url(String url) {
			this.url = url;
			return this;
		}

		public Builder numberOfSubscribers(int numberOfSubscribers) {
			this.numberOfSubscribers = numberOfSubscribers;
			return this;
		}

		public Builder language(String language) {
			this.language = language;
			return this;
		}

		public Builder type(CommunityEngagementTypeEnum type) {
			this.type = type;
			return this;
		}

		public Builder toCreate() {
			this.isCreated = true;
			return this;
		}

		public CommunityEngagement build() {
			if (isCreated) {
				this.creationDate = new Date();
			} else {
				this.updateDate = new Date();
			}
			return new CommunityEngagement(this);
		}
	}
}
