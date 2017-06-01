package com.onnisoft.wahoo.api.request;

import java.io.Serializable;
import java.util.Map;

public class TeamFormationRequestDTO implements Serializable {

	private static final long serialVersionUID = 2391917933593381500L;
	private String competitorId;
	private String roundId;
	// key: playerId, value: PlayerGamePositionEnum String
	private Map<String, String> playersPositions;
	private String formation;

	public TeamFormationRequestDTO() {
	}

	public TeamFormationRequestDTO(String competitorId, String roundId, Map<String, String> playersPositions, String formation) {
		this.competitorId = competitorId;
		this.roundId = roundId;
		this.playersPositions = playersPositions;
		this.formation = formation;
	}

	public String getCompetitorId() {
		return competitorId;
	}

	public String getRoundId() {
		return roundId;
	}

	public Map<String, String> getPlayersPositions() {
		return playersPositions;
	}

	public String getFormation() {
		return formation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((competitorId == null) ? 0 : competitorId.hashCode());
		result = prime * result + ((formation == null) ? 0 : formation.hashCode());
		result = prime * result + ((playersPositions == null) ? 0 : playersPositions.hashCode());
		result = prime * result + ((roundId == null) ? 0 : roundId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TeamFormationRequestDTO other = (TeamFormationRequestDTO) obj;
		if (competitorId == null) {
			if (other.competitorId != null)
				return false;
		} else if (!competitorId.equals(other.competitorId))
			return false;
		if (formation == null) {
			if (other.formation != null)
				return false;
		} else if (!formation.equals(other.formation))
			return false;
		if (playersPositions == null) {
			if (other.playersPositions != null)
				return false;
		} else if (!playersPositions.equals(other.playersPositions))
			return false;
		if (roundId == null) {
			if (other.roundId != null)
				return false;
		} else if (!roundId.equals(other.roundId))
			return false;
		return true;
	}
}
