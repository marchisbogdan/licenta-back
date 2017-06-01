package com.onnisoft.wahoo.model.document;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Deprecated
@Document(collection = "performance_statistics")
public class PerformanceStatistic extends Node implements Serializable {

	private static final long serialVersionUID = -1859269981114492388L;
	private int pointPerGame;
	private int projectedPointPerGame;
	private int currentPosition;
	private int projectedFinishingPosition;

	/**
	 * Used for json serialization/deserialization.
	 */
	public PerformanceStatistic() {
	}

	/**
	 * @param id
	 * @param pointPerGame
	 * @param projectedPointPerGame
	 * @param currentPosition
	 * @param projectedFinishingPosition
	 */
	private PerformanceStatistic(Builder builder) {
		super(builder.id, builder.creationDate, builder.updateDate);
		this.pointPerGame = builder.pointPerGame;
		this.projectedPointPerGame = builder.projectedPointPerGame;
		this.currentPosition = builder.currentPosition;
		this.projectedFinishingPosition = builder.projectedFinishingPosition;
	}

	/**
	 * @return the pointPerGame
	 */
	public int getPointPerGame() {
		return this.pointPerGame;
	}

	/**
	 * @return the projectedPointPerGame
	 */
	public int getProjectedPointPerGame() {
		return this.projectedPointPerGame;
	}

	/**
	 * @return the currentPosition
	 */
	public int getCurrentPosition() {
		return this.currentPosition;
	}

	/**
	 * @return the projectedFinishingPosition
	 */
	public int getProjectedFinishingPosition() {
		return this.projectedFinishingPosition;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + currentPosition;
		result = prime * result + pointPerGame;
		result = prime * result + projectedFinishingPosition;
		result = prime * result + projectedPointPerGame;
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
		PerformanceStatistic other = (PerformanceStatistic) obj;
		if (currentPosition != other.currentPosition)
			return false;
		if (pointPerGame != other.pointPerGame)
			return false;
		if (projectedFinishingPosition != other.projectedFinishingPosition)
			return false;
		if (projectedPointPerGame != other.projectedPointPerGame)
			return false;
		return true;
	}

	public static final class Builder {
		private String id;
		private int pointPerGame;
		private int projectedPointPerGame;
		private int currentPosition;
		private int projectedFinishingPosition;

		private boolean isCreated;

		private Date creationDate;
		private Date updateDate;

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder pointPerGame(int pointPerGame) {
			this.pointPerGame = pointPerGame;
			return this;
		}

		public Builder projectedPointPerGame(int projectedPointPerGame) {
			this.projectedPointPerGame = projectedPointPerGame;
			return this;
		}

		public Builder currentPosition(int currentPosition) {
			this.currentPosition = currentPosition;
			return this;
		}

		public Builder projectedFinishingPosition(int projectedFinishingPosition) {
			this.projectedFinishingPosition = projectedFinishingPosition;
			return this;
		}

		public Builder toCreate() {
			this.isCreated = true;
			return this;
		}

		public PerformanceStatistic build() {
			if (isCreated) {
				this.creationDate = new Date();
			} else {
				this.updateDate = new Date();
			}
			return new PerformanceStatistic(this);
		}
	}
}
