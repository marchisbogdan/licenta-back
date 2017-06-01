package com.onnisoft.wahoo.model.document;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.onnisoft.wahoo.model.document.enums.AchievementStatusEnum;
import com.onnisoft.wahoo.model.document.enums.AchievementTypeEnum;

/**
 * 
 * Entity that keeps information about achievements. The achievements can be
 * medals, badges, stars, honors and their status can be gold, silver, bronze,
 * chrome, platinum.
 *
 * @author mbozesan
 * @date 22 Sep 2016 - 11:30:10
 *
 */
@Document(collection = "achievements")
public class Achievement extends Node implements Serializable {

	private static final long serialVersionUID = -6080131852156329045L;

	private AchievementTypeEnum achievementType;
	private AchievementStatusEnum achievementStatus;
	private String title;

	public Achievement() {

	}

	private Achievement(Builder builder) {
		super(builder.id, builder.creationDate, builder.updateDate);
		this.achievementType = builder.achievementType;
		this.achievementStatus = builder.achievementStatus;
		this.title = builder.title;
	}

	/**
	 * @return the achievementType
	 */
	public AchievementTypeEnum getAchievementType() {
		return achievementType;
	}

	/**
	 * @return the achievementStatus
	 */
	public AchievementStatusEnum getAchievementStatus() {
		return achievementStatus;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((achievementStatus == null) ? 0 : achievementStatus.hashCode());
		result = prime * result + ((achievementType == null) ? 0 : achievementType.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Achievement other = (Achievement) obj;
		if (achievementStatus != other.achievementStatus)
			return false;
		if (achievementType != other.achievementType)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	public static final class Builder {
		private String id;
		private AchievementTypeEnum achievementType;
		private AchievementStatusEnum achievementStatus;
		private String title;

		private boolean isCreated;

		private Date creationDate;
		private Date updateDate;

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder achievementType(AchievementTypeEnum achievementType) {
			this.achievementType = achievementType;
			return this;
		}

		public Builder achievementStatus(AchievementStatusEnum achievementStatus) {
			this.achievementStatus = achievementStatus;
			return this;
		}

		public Builder title(String title) {
			this.title = title;
			return this;
		}

		public Builder toCreate() {
			this.isCreated = true;
			return this;
		}

		public Achievement build() {
			if (isCreated) {
				this.creationDate = new Date();
			} else {
				this.updateDate = new Date();
			}
			return new Achievement(this);
		}
	}
}