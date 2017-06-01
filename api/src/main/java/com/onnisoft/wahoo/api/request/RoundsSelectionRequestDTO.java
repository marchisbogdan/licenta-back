package com.onnisoft.wahoo.api.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * 
 * Rounds selection request. It contains a list of round ids and the id of the
 * virtual competition to which we assign these rounds. Also, it contains a
 * selectedAll boolean which if true, will result in neglecting the roundIds and
 * pulling all the rounds of the season from database.
 *
 * @author mbozesan
 * @date 8 Nov 2016 - 11:17:21
 *
 */
public class RoundsSelectionRequestDTO implements Serializable {

	private static final long serialVersionUID = 7542823680317261567L;

	@NotNull
	private String virtualCompetitionId;
	@NotNull
	private String seasonId;
	@NotNull
	private List<String> roundIds;
	private boolean selectedAll;

	public RoundsSelectionRequestDTO() {
	}

	public RoundsSelectionRequestDTO(String virtualCompetitionId, String seasonId, List<String> roundIds, boolean selectedAll) {
		this.virtualCompetitionId = virtualCompetitionId;
		this.seasonId = seasonId;
		this.roundIds = roundIds;
		this.selectedAll = selectedAll;
	}

	/**
	 * @return the virtualCompetitionId
	 */
	public String getVirtualCompetitionId() {
		return virtualCompetitionId;
	}

	/**
	 * @return the seasonId
	 */
	public String getSeasonId() {
		return seasonId;
	}

	/**
	 * @return the eventIds
	 */
	public List<String> getRoundIds() {
		return this.roundIds == null ? roundIds : new ArrayList<>(this.roundIds);
	}

	/**
	 * @return the all
	 */
	public boolean isSelectedAll() {
		return selectedAll;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((roundIds == null) ? 0 : roundIds.hashCode());
		result = prime * result + ((seasonId == null) ? 0 : seasonId.hashCode());
		result = prime * result + (selectedAll ? 1231 : 1237);
		result = prime * result + ((virtualCompetitionId == null) ? 0 : virtualCompetitionId.hashCode());
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
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoundsSelectionRequestDTO other = (RoundsSelectionRequestDTO) obj;
		if (roundIds == null) {
			if (other.roundIds != null)
				return false;
		} else if (!roundIds.equals(other.roundIds))
			return false;
		if (seasonId == null) {
			if (other.seasonId != null)
				return false;
		} else if (!seasonId.equals(other.seasonId))
			return false;
		if (selectedAll != other.selectedAll)
			return false;
		if (virtualCompetitionId == null) {
			if (other.virtualCompetitionId != null)
				return false;
		} else if (!virtualCompetitionId.equals(other.virtualCompetitionId))
			return false;
		return true;
	}
}
