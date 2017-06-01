package com.onnisoft.wahoo.api.request;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.onnisoft.wahoo.model.document.enums.CompetitionTypeEnum;
import com.onnisoft.wahoo.model.document.enums.RulesAndPointSystemEnum;

/**
 * 
 * Competition creation request.
 *
 * @author mbozesan
 * @date 21 Oct 2016 - 10:24:41
 *
 */
public class CompetitionCreationRequestDTO implements Serializable {

	private static final long serialVersionUID = 4672027611155494229L;

	@NotNull
	private String name;
	private String secondaryName;
	private CompetitionTypeEnum type;
	private Integer maxNumParticipants;
	private Integer maxEntries;
	private String info;
	private String avatarLink;
	private List<String> eventsIds;
	private String prizeId;
	private String marketId;
	private RulesAndPointSystemEnum rulesAndPointSystem;
	private Boolean territoryLimitedAccess;
	private Boolean linkLimitedAccess;
	private Boolean promotion;
	private Date launchDateTime;

	public CompetitionCreationRequestDTO() {
	}

	public CompetitionCreationRequestDTO(String name, String secondaryName, CompetitionTypeEnum type, Integer maxNumParticipants, Integer maxEntries,
			String info, String avatarLink, List<String> eventsIds, String prizeId, String marketId, RulesAndPointSystemEnum rulesAndPointSystem,
			Boolean territoryLimitedAccess, Boolean linkLimitedAccess, Boolean promotion, Date launchDateTime) {
		super();
		this.name = name;
		this.secondaryName = secondaryName;
		this.type = type;
		this.maxNumParticipants = maxNumParticipants;
		this.maxEntries = maxEntries;
		this.info = info;
		this.avatarLink = avatarLink;
		this.eventsIds = eventsIds;
		this.prizeId = prizeId;
		this.marketId = marketId;
		this.rulesAndPointSystem = rulesAndPointSystem;
		this.territoryLimitedAccess = territoryLimitedAccess;
		this.linkLimitedAccess = linkLimitedAccess;
		this.promotion = promotion;
		this.launchDateTime = launchDateTime;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the secondaryName
	 */
	public String getSecondaryName() {
		return secondaryName;
	}

	/**
	 * @return the type
	 */
	public CompetitionTypeEnum getType() {
		return type;
	}

	/**
	 * @return the maxNumParticipants
	 */
	public Integer getMaxNumParticipants() {
		return maxNumParticipants;
	}

	/**
	 * @return the info
	 */
	public String getInfo() {
		return info;
	}

	/**
	 * @return the avatarLink
	 */
	public String getAvatarLink() {
		return avatarLink;
	}

	/**
	 * @return the prize
	 */
	public String getPrizeId() {
		return prizeId;
	}

	/**
	 * @return the closed
	 */
	public Boolean isTerritoryLimitedAccess() {
		return territoryLimitedAccess;
	}

	/**
	 * @return the linkLimitedAccess
	 */
	public Boolean isLinkLimitedAccess() {
		return linkLimitedAccess;
	}

	/**
	 * 
	 * @return the number of max entries
	 */
	public Integer getMaxEntries() {
		return maxEntries;
	}

	/**
	 * 
	 * @return the rules and point system
	 */
	public RulesAndPointSystemEnum getRulesAndPointSystem() {
		return rulesAndPointSystem;
	}

	/**
	 * 
	 * @return true or false If the competition shall figure in featured
	 *         promotion area
	 */
	public Boolean isPromotion() {
		return promotion;
	}

	/**
	 * 
	 * @return the launch date and time for the competition to be active and
	 *         visible in the game lobby
	 */
	public Date getLaunchDateTime() {
		return launchDateTime;
	}

	/**
	 * 
	 * @return a list with the IDs of the events which the competition will
	 *         contain.
	 */
	public List<String> getEventsIds() {
		return eventsIds;
	}

	/**
	 * return the market id.
	 * 
	 * @return
	 */
	public String getMarketId() {
		return marketId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((avatarLink == null) ? 0 : avatarLink.hashCode());
		result = prime * result + ((eventsIds == null) ? 0 : eventsIds.hashCode());
		result = prime * result + ((info == null) ? 0 : info.hashCode());
		result = prime * result + ((launchDateTime == null) ? 0 : launchDateTime.hashCode());
		result = prime * result + (linkLimitedAccess ? 1231 : 1237);
		result = prime * result + ((marketId == null) ? 0 : marketId.hashCode());
		result = prime * result + maxEntries;
		result = prime * result + maxNumParticipants;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((prizeId == null) ? 0 : prizeId.hashCode());
		result = prime * result + (promotion ? 1231 : 1237);
		result = prime * result + ((rulesAndPointSystem == null) ? 0 : rulesAndPointSystem.hashCode());
		result = prime * result + ((secondaryName == null) ? 0 : secondaryName.hashCode());
		result = prime * result + (territoryLimitedAccess ? 1231 : 1237);
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		CompetitionCreationRequestDTO other = (CompetitionCreationRequestDTO) obj;
		if (avatarLink == null) {
			if (other.avatarLink != null)
				return false;
		} else if (!avatarLink.equals(other.avatarLink))
			return false;
		if (eventsIds == null) {
			if (other.eventsIds != null)
				return false;
		} else if (!eventsIds.equals(other.eventsIds))
			return false;
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		if (launchDateTime == null) {
			if (other.launchDateTime != null)
				return false;
		} else if (!launchDateTime.equals(other.launchDateTime))
			return false;
		if (linkLimitedAccess != other.linkLimitedAccess)
			return false;
		if (marketId == null) {
			if (other.marketId != null)
				return false;
		} else if (!marketId.equals(other.marketId))
			return false;
		if (maxEntries != other.maxEntries)
			return false;
		if (maxNumParticipants != other.maxNumParticipants)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (prizeId == null) {
			if (other.prizeId != null)
				return false;
		} else if (!prizeId.equals(other.prizeId))
			return false;
		if (promotion != other.promotion)
			return false;
		if (rulesAndPointSystem != other.rulesAndPointSystem)
			return false;
		if (secondaryName == null) {
			if (other.secondaryName != null)
				return false;
		} else if (!secondaryName.equals(other.secondaryName))
			return false;
		if (territoryLimitedAccess != other.territoryLimitedAccess)
			return false;
		if (type != other.type)
			return false;
		return true;
	}
}
