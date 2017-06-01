package com.onnisoft.wahoo.api.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * 
 * Group join request.
 *
 * @author mbozesan
 * @date 21 Oct 2016 - 10:34:55
 *
 */
public class GroupJoinRequestDTO implements Serializable {

	private static final long serialVersionUID = 7697868155104077295L;

	@NotNull
	private String competitionGroupId;
	@NotNull
	private String competitorId;
	private String password;

	public GroupJoinRequestDTO() {
	}

	public GroupJoinRequestDTO(String competitionGroupId, String competitorId, String password) {
		this.competitionGroupId = competitionGroupId;
		this.competitorId = competitorId;
		this.password = password;
	}

	/**
	 * @return the competitionGroup
	 */
	public String getCompetitionGroupId() {
		return competitionGroupId;
	}

	/**
	 * @return the competitors
	 */
	public String getCompetitorId() {
		return competitorId;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
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
		result = prime * result + ((competitionGroupId == null) ? 0 : competitionGroupId.hashCode());
		result = prime * result + ((competitorId == null) ? 0 : competitorId.hashCode());
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
		GroupJoinRequestDTO other = (GroupJoinRequestDTO) obj;
		if (competitionGroupId == null) {
			if (other.competitionGroupId != null)
				return false;
		} else if (!competitionGroupId.equals(other.competitionGroupId))
			return false;
		if (competitorId == null) {
			if (other.competitorId != null)
				return false;
		} else if (!competitorId.equals(other.competitorId))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}
}
