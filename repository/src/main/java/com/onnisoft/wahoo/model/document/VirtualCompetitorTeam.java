package com.onnisoft.wahoo.model.document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.onnisoft.wahoo.model.document.enums.FieldFormationEnum;
import com.onnisoft.wahoo.model.document.enums.PlayerGamePositionEnum;

@Document(collection = "virtual-competitor-team")
public class VirtualCompetitorTeam extends Node implements Serializable {

	private static final long serialVersionUID = 4745578517591151918L;

	@DBRef
	private VirtualCompetitor virtualCompetitor;
	@DBRef
	private Round round;

	@DBRef
	private List<Player> team;

	private Map<String, PlayerGamePositionEnum> positions;

	@DBRef
	private Player captain;

	private int points;
	private FieldFormationEnum formation;

	public VirtualCompetitorTeam() {
	}

	private VirtualCompetitorTeam(Builder builder) {
		super(builder.id, builder.creationDate, builder.updateDate);
		this.virtualCompetitor = builder.virtualCompetitor;
		this.round = builder.round;
		this.team = builder.team;
		this.positions = builder.positions;
		this.captain = builder.captain;
		this.points = builder.points;
		this.formation = builder.formation;
	}

	public VirtualCompetitor getVirtualCompetitor() {
		return virtualCompetitor;
	}

	public Round getRound() {
		return round;
	}

	public List<Player> getTeam() {
		return team;
	}

	public Map<String, PlayerGamePositionEnum> getPositions() {
		return positions;
	}

	public Player getCaptain() {
		return captain;
	}

	public int getPoints() {
		return points;
	}

	public FieldFormationEnum getFormation() {
		return formation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((captain == null) ? 0 : captain.hashCode());
		result = prime * result + ((formation == null) ? 0 : formation.hashCode());
		result = prime * result + points;
		result = prime * result + ((positions == null) ? 0 : positions.hashCode());
		result = prime * result + ((round == null) ? 0 : round.hashCode());
		result = prime * result + ((team == null) ? 0 : team.hashCode());
		result = prime * result + ((virtualCompetitor == null) ? 0 : virtualCompetitor.hashCode());
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
		VirtualCompetitorTeam other = (VirtualCompetitorTeam) obj;
		if (captain == null) {
			if (other.captain != null)
				return false;
		} else if (!captain.equals(other.captain))
			return false;
		if (formation != other.formation)
			return false;
		if (points != other.points)
			return false;
		if (positions == null) {
			if (other.positions != null)
				return false;
		} else if (!positions.equals(other.positions))
			return false;
		if (round == null) {
			if (other.round != null)
				return false;
		} else if (!round.equals(other.round))
			return false;
		if (team == null) {
			if (other.team != null)
				return false;
		} else if (!team.equals(other.team))
			return false;
		if (virtualCompetitor == null) {
			if (other.virtualCompetitor != null)
				return false;
		} else if (!virtualCompetitor.equals(other.virtualCompetitor))
			return false;
		return true;
	}

	public static final class Builder {
		private String id;
		private Round round;
		private VirtualCompetitor virtualCompetitor;
		private List<Player> team;
		private Map<String, PlayerGamePositionEnum> positions;
		private Player captain;
		private int points;
		private FieldFormationEnum formation;

		private boolean isCreated;

		private Date creationDate;
		private Date updateDate;

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder round(Round round) {
			this.round = round;
			return this;
		}

		public Builder virtualCompetitor(VirtualCompetitor virtualCompetitor) {
			this.virtualCompetitor = virtualCompetitor;
			return this;
		}

		public Builder team(List<Player> team) {
			this.team = team;
			return this;
		}

		public Builder positions(Map<String, PlayerGamePositionEnum> positions) {
			this.positions = positions;
			return this;
		}

		public Builder captain(Player captain) {
			this.captain = captain;
			return this;
		}

		public Builder points(int points) {
			this.points = points;
			return this;
		}

		public Builder formation(FieldFormationEnum formation) {
			this.formation = formation;
			return this;
		}

		public Builder toCreate() {
			this.isCreated = true;
			return this;
		}

		public VirtualCompetitorTeam build() {
			if (isCreated) {
				this.creationDate = new Date();
			} else {
				this.updateDate = new Date();
			}
			return new VirtualCompetitorTeam(this);
		}
	}
}
