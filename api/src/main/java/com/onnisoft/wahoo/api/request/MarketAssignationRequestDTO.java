package com.onnisoft.wahoo.api.request;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.onnisoft.wahoo.model.document.enums.GenderEnum;

public class MarketAssignationRequestDTO implements Serializable {

	private static final long serialVersionUID = 6190244880822089973L;

	@NotNull
	private String virtualCompetitionId;
	private String name;
	private List<String> countryIds;
	private String partnerId;
	private GenderEnum targetGender;
	private int targetAge;
	// TODO: This is a placeholder. To be replaced with an object when more is
	// known about what the targetInterests will be.
	private String targetInterests;

	public MarketAssignationRequestDTO() {
	}

	public MarketAssignationRequestDTO(String virtualCompetitionId, String name, List<String> countryIds, String partnerId, GenderEnum targetGender,
			int targetAge, String targetInterests) {
		super();
		this.virtualCompetitionId = virtualCompetitionId;
		this.name = name;
		this.countryIds = countryIds;
		this.partnerId = partnerId;
		this.targetGender = targetGender;
		this.targetAge = targetAge;
		this.targetInterests = targetInterests;
	}

	public String getName() {
		return name;
	}

	public String getVirtualCompetitionId() {
		return virtualCompetitionId;
	}

	public List<String> getCountryIds() {
		return countryIds;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public GenderEnum getTargetGender() {
		return targetGender;
	}

	public int getTargetAge() {
		return targetAge;
	}

	public String getTargetInterests() {
		return targetInterests;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((countryIds == null) ? 0 : countryIds.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((partnerId == null) ? 0 : partnerId.hashCode());
		result = prime * result + targetAge;
		result = prime * result + ((targetGender == null) ? 0 : targetGender.hashCode());
		result = prime * result + ((targetInterests == null) ? 0 : targetInterests.hashCode());
		result = prime * result + ((virtualCompetitionId == null) ? 0 : virtualCompetitionId.hashCode());
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
		MarketAssignationRequestDTO other = (MarketAssignationRequestDTO) obj;
		if (countryIds == null) {
			if (other.countryIds != null)
				return false;
		} else if (!countryIds.equals(other.countryIds))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (partnerId == null) {
			if (other.partnerId != null)
				return false;
		} else if (!partnerId.equals(other.partnerId))
			return false;
		if (targetAge != other.targetAge)
			return false;
		if (targetGender != other.targetGender)
			return false;
		if (targetInterests == null) {
			if (other.targetInterests != null)
				return false;
		} else if (!targetInterests.equals(other.targetInterests))
			return false;
		if (virtualCompetitionId == null) {
			if (other.virtualCompetitionId != null)
				return false;
		} else if (!virtualCompetitionId.equals(other.virtualCompetitionId))
			return false;
		return true;
	}

}
