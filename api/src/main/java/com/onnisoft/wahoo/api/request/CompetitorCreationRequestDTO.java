package com.onnisoft.wahoo.api.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * 
 * Competitor creation request.
 *
 * @author mbozesan
 * @date 21 Oct 2016 - 10:31:43
 *
 */
public class CompetitorCreationRequestDTO implements Serializable {

	private static final long serialVersionUID = 6818543800915932052L;

	@NotNull
	private String name;
	@NotNull
	private String competitionId;

	public CompetitorCreationRequestDTO() {
	}

	public CompetitorCreationRequestDTO(String name, String competitionId) {
		this.name = name;
		this.competitionId = competitionId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the competitionId
	 */
	public String getCompetitionId() {
		return competitionId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((competitionId == null) ? 0 : competitionId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		CompetitorCreationRequestDTO other = (CompetitorCreationRequestDTO) obj;
		if (competitionId == null) {
			if (other.competitionId != null)
				return false;
		} else if (!competitionId.equals(other.competitionId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
