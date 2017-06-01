package com.onnisoft.wahoo.model.document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.onnisoft.wahoo.model.document.enums.CompetitionTypeEnum;
import com.onnisoft.wahoo.model.document.enums.RulesAndPointSystemEnum;

/**
 * 
 * Entity that keeps information about competitions.
 *
 * @author mbozesan
 * @date 23 Sep 2016 - 16:55:12
 *
 */
@Document(collection = "virtual-competitions")
public class VirtualCompetition extends Node implements Serializable {

	private static final long serialVersionUID = 1589478915259677041L;

	private String name;
	private String secondaryName;
	private CompetitionTypeEnum type;
	private Integer maxNumParticipants;
	private Integer maxEntries;
	private String info;
	private String avatarLink;
	@DBRef
	private Market market;
	@DBRef
	private Sport sport;
	@DBRef
	private RealCompetition realCompetition;
	@DBRef
	private Season season;
	@DBRef
	private List<Round> rounds;
	@DBRef
	private List<Event> events;
	@DBRef
	private Prize prize;
	private RulesAndPointSystemEnum rulesAndPointSystem;
	private Boolean territoryLimitedAccess;
	private Boolean linkLimitedAccess;
	private Boolean promotion;
	private Integer numberOfSeats;
	private Integer seatValue;
	private Date startDateTime;
	private Date endDateTime;
	private Date launchDateTime;

	public VirtualCompetition() {
	}

	private VirtualCompetition(VirtualCompetitionBuilder builder) {
		super(builder.id, builder.creationDate, builder.updateDate);
		this.name = builder.name;
		this.secondaryName = builder.secondaryName;
		this.type = builder.type;
		this.maxNumParticipants = builder.maxNumParticipants;
		this.maxEntries = builder.maxEntries;
		this.info = builder.info;
		this.avatarLink = builder.avatarLink;
		this.market = builder.market;
		this.sport = builder.sport;
		this.realCompetition = builder.realCompetition;
		this.season = builder.season;
		this.rounds = builder.rounds;
		this.events = builder.events;
		this.prize = builder.prize;
		this.rulesAndPointSystem = builder.rulesAndPointSystem;
		this.territoryLimitedAccess = builder.territoryLimitedAccess;
		this.linkLimitedAccess = builder.linkLimitedAccess;
		this.promotion = builder.promotion;
		this.numberOfSeats = builder.numberOfSeats;
		this.seatValue = builder.seatValue;
		this.startDateTime = builder.startDateTime;
		this.endDateTime = builder.endDateTime;
		this.launchDateTime = builder.launchDateTime;
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
	 * @return the market
	 */
	public Market getMarket() {
		return market;
	}

	/**
	 * @return the game
	 */
	public Sport getSport() {
		return sport;
	}

	/**
	 * @return the realCompetitionId
	 */
	public RealCompetition getRealCompetition() {
		return realCompetition;
	}

	/**
	 * @return the seasonId
	 */
	public Season getSeason() {
		return season;
	}

	/**
	 * @return the roundIds
	 */
	public List<Round> getRounds() {
		return rounds;
	}

	/**
	 * @return the eventIds
	 */
	public List<Event> getEvents() {
		return events;
	}

	/**
	 * 
	 * @return the prize
	 */
	public Prize getPrize() {
		return prize;
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
	 * @return the numberOfSeats
	 */
	public Integer getNumberOfSeats() {
		return numberOfSeats;
	}

	/**
	 * @return the seatValue
	 */
	public Integer getSeatValue() {
		return seatValue;
	}

	/**
	 * @return the startDateTime
	 */
	public Date getStartDateTime() {
		return startDateTime;
	}

	/**
	 * @return the endDateTime
	 */
	public Date getEndDateTime() {
		return endDateTime;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((avatarLink == null) ? 0 : avatarLink.hashCode());
		result = prime * result + ((endDateTime == null) ? 0 : endDateTime.hashCode());
		result = prime * result + ((events == null) ? 0 : events.hashCode());
		result = prime * result + ((info == null) ? 0 : info.hashCode());
		result = prime * result + ((launchDateTime == null) ? 0 : launchDateTime.hashCode());
		result = prime * result + ((linkLimitedAccess == null) ? 0 : linkLimitedAccess.hashCode());
		result = prime * result + ((market == null) ? 0 : market.hashCode());
		result = prime * result + ((maxEntries == null) ? 0 : maxEntries.hashCode());
		result = prime * result + ((maxNumParticipants == null) ? 0 : maxNumParticipants.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((numberOfSeats == null) ? 0 : numberOfSeats.hashCode());
		result = prime * result + ((prize == null) ? 0 : prize.hashCode());
		result = prime * result + ((promotion == null) ? 0 : promotion.hashCode());
		result = prime * result + ((realCompetition == null) ? 0 : realCompetition.hashCode());
		result = prime * result + ((rounds == null) ? 0 : rounds.hashCode());
		result = prime * result + ((rulesAndPointSystem == null) ? 0 : rulesAndPointSystem.hashCode());
		result = prime * result + ((season == null) ? 0 : season.hashCode());
		result = prime * result + ((seatValue == null) ? 0 : seatValue.hashCode());
		result = prime * result + ((secondaryName == null) ? 0 : secondaryName.hashCode());
		result = prime * result + ((sport == null) ? 0 : sport.hashCode());
		result = prime * result + ((startDateTime == null) ? 0 : startDateTime.hashCode());
		result = prime * result + ((territoryLimitedAccess == null) ? 0 : territoryLimitedAccess.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		VirtualCompetition other = (VirtualCompetition) obj;
		if (avatarLink == null) {
			if (other.avatarLink != null)
				return false;
		} else if (!avatarLink.equals(other.avatarLink))
			return false;
		if (endDateTime == null) {
			if (other.endDateTime != null)
				return false;
		} else if (!endDateTime.equals(other.endDateTime))
			return false;
		if (events == null) {
			if (other.events != null)
				return false;
		} else if (!events.equals(other.events))
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
		if (linkLimitedAccess == null) {
			if (other.linkLimitedAccess != null)
				return false;
		} else if (!linkLimitedAccess.equals(other.linkLimitedAccess))
			return false;
		if (market == null) {
			if (other.market != null)
				return false;
		} else if (!market.equals(other.market))
			return false;
		if (maxEntries == null) {
			if (other.maxEntries != null)
				return false;
		} else if (!maxEntries.equals(other.maxEntries))
			return false;
		if (maxNumParticipants == null) {
			if (other.maxNumParticipants != null)
				return false;
		} else if (!maxNumParticipants.equals(other.maxNumParticipants))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (numberOfSeats == null) {
			if (other.numberOfSeats != null)
				return false;
		} else if (!numberOfSeats.equals(other.numberOfSeats))
			return false;
		if (prize == null) {
			if (other.prize != null)
				return false;
		} else if (!prize.equals(other.prize))
			return false;
		if (promotion == null) {
			if (other.promotion != null)
				return false;
		} else if (!promotion.equals(other.promotion))
			return false;
		if (realCompetition == null) {
			if (other.realCompetition != null)
				return false;
		} else if (!realCompetition.equals(other.realCompetition))
			return false;
		if (rounds == null) {
			if (other.rounds != null)
				return false;
		} else if (!rounds.equals(other.rounds))
			return false;
		if (rulesAndPointSystem != other.rulesAndPointSystem)
			return false;
		if (season == null) {
			if (other.season != null)
				return false;
		} else if (!season.equals(other.season))
			return false;
		if (seatValue == null) {
			if (other.seatValue != null)
				return false;
		} else if (!seatValue.equals(other.seatValue))
			return false;
		if (secondaryName == null) {
			if (other.secondaryName != null)
				return false;
		} else if (!secondaryName.equals(other.secondaryName))
			return false;
		if (sport == null) {
			if (other.sport != null)
				return false;
		} else if (!sport.equals(other.sport))
			return false;
		if (startDateTime == null) {
			if (other.startDateTime != null)
				return false;
		} else if (!startDateTime.equals(other.startDateTime))
			return false;
		if (territoryLimitedAccess == null) {
			if (other.territoryLimitedAccess != null)
				return false;
		} else if (!territoryLimitedAccess.equals(other.territoryLimitedAccess))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	public static final class VirtualCompetitionBuilder {
		private String id;
		private String name;
		private String secondaryName;
		private CompetitionTypeEnum type;
		private Integer maxNumParticipants;
		private Integer maxEntries;
		private String info;
		private String avatarLink;
		private Market market;
		private Sport sport;
		private RealCompetition realCompetition;
		private Season season;
		private List<Round> rounds;
		private List<Event> events;
		private Prize prize;
		private RulesAndPointSystemEnum rulesAndPointSystem;
		private Boolean territoryLimitedAccess;
		private Boolean linkLimitedAccess;
		private Boolean promotion;
		private Integer numberOfSeats;
		private Integer seatValue;
		private Date startDateTime;
		private Date endDateTime;
		private Date launchDateTime;

		private Date creationDate;
		private Date updateDate;

		private boolean isCreated;

		public VirtualCompetitionBuilder() {

		}

		public VirtualCompetitionBuilder toCreate() {
			this.isCreated = true;
			return this;
		}

		public VirtualCompetitionBuilder id(String id) {
			this.id = id;
			return this;
		}

		public VirtualCompetitionBuilder name(String name) {
			this.name = name;
			return this;
		}

		public VirtualCompetitionBuilder secondaryName(String secondaryName) {
			this.secondaryName = secondaryName;
			return this;
		}

		public VirtualCompetitionBuilder type(CompetitionTypeEnum type) {
			this.type = type;
			return this;
		}

		public VirtualCompetitionBuilder maxNumParticipants(Integer maxNumParticipants) {
			this.maxNumParticipants = maxNumParticipants;
			return this;
		}

		public VirtualCompetitionBuilder maxEntries(Integer maxEntries) {
			this.maxEntries = maxEntries;
			return this;
		}

		public VirtualCompetitionBuilder info(String info) {
			this.info = info;
			return this;
		}

		public VirtualCompetitionBuilder avatarLink(String avatarLink) {
			this.avatarLink = avatarLink;
			return this;
		}

		public VirtualCompetitionBuilder market(Market market) {
			this.market = market;
			return this;
		}

		public VirtualCompetitionBuilder sport(Sport sport) {
			this.sport = sport;
			return this;
		}

		public VirtualCompetitionBuilder realCompetition(RealCompetition realCompetition) {
			this.realCompetition = realCompetition;
			return this;
		}

		public VirtualCompetitionBuilder season(Season season) {
			this.season = season;
			return this;
		}

		public VirtualCompetitionBuilder rounds(List<Round> rounds) {
			this.rounds = rounds;
			return this;
		}

		public VirtualCompetitionBuilder events(List<Event> events) {
			this.events = events;
			return this;
		}

		public VirtualCompetitionBuilder prize(Prize prize) {
			this.prize = prize;
			return this;
		}

		public VirtualCompetitionBuilder rulesAndPointSystem(RulesAndPointSystemEnum rulesAndPointSystem) {
			this.rulesAndPointSystem = rulesAndPointSystem;
			return this;
		}

		public VirtualCompetitionBuilder territoryLimitedAccess(Boolean territoryLimitedAccess) {
			this.territoryLimitedAccess = territoryLimitedAccess;
			return this;
		}

		public VirtualCompetitionBuilder linkLimitedAccess(Boolean linkLimitedAccess) {
			this.linkLimitedAccess = linkLimitedAccess;
			return this;
		}

		public VirtualCompetitionBuilder promotion(Boolean promotion) {
			this.promotion = promotion;
			return this;
		}

		public VirtualCompetitionBuilder numberOfSeats(Integer numberOfSeats) {
			this.numberOfSeats = numberOfSeats;
			return this;
		}

		public VirtualCompetitionBuilder seatValue(Integer seatValue) {
			this.seatValue = seatValue;
			return this;
		}

		public VirtualCompetitionBuilder startDateTime(Date startDateTime) {
			this.startDateTime = startDateTime;
			return this;
		}

		public VirtualCompetitionBuilder endDateTime(Date endDateTime) {
			this.endDateTime = endDateTime;
			return this;
		}

		public VirtualCompetitionBuilder launchDateTime(Date launchDateTime) {
			this.launchDateTime = launchDateTime;
			return this;
		}

		public VirtualCompetition build() {
			if (isCreated) {
				this.creationDate = new Date();
			} else {
				this.updateDate = new Date();
			}
			return new VirtualCompetition(this);
		}
	}
}
