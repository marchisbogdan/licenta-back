package com.onnisoft.wahoo.api.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * 
 * Group creation request.
 *
 * @author mbozesan
 * @date 21 Oct 2016 - 10:32:22
 *
 */
public class GroupCreationRequestDTO implements Serializable {

	private static final long serialVersionUID = -5518532820971611625L;

	@NotNull
	private String name;
	private String password;
	private String competitorId;
	private String competitionId;

	public GroupCreationRequestDTO() {
	}

	public GroupCreationRequestDTO(String name, String password, String competitorId, String competitionId) {
		this.name = name;
		this.password = password;
		this.competitorId = competitorId;
		this.competitionId = competitionId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return the competitor
	 */
	public String getCompetitorId() {
		return competitorId;
	}

	/**
	 * @return the competition
	 */
	public String getCompetitionId() {
		return competitionId;
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
		result = prime * result + ((competitionId == null) ? 0 : competitionId.hashCode());
		result = prime * result + ((competitorId == null) ? 0 : competitorId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
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
		GroupCreationRequestDTO other = (GroupCreationRequestDTO) obj;
		if (competitionId == null) {
			if (other.competitionId != null)
				return false;
		} else if (!competitionId.equals(other.competitionId))
			return false;
		if (competitorId == null) {
			if (other.competitorId != null)
				return false;
		} else if (!competitorId.equals(other.competitorId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}
}
