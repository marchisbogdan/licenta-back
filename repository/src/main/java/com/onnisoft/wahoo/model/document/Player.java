package com.onnisoft.wahoo.model.document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.onnisoft.wahoo.model.document.enums.PlayerPositionEnum;
import com.onnisoft.wahoo.model.document.enums.PlayerStateEnum;
import com.onnisoft.wahoo.model.document.enums.StatusEnum;

/**
 * 
 * Entity that keeps information about players. A player is a type of athlete
 * that belongs to a team and has the attribute of position within the team.
 *
 * @author mbozesan
 * @date 30 Sep 2016 - 13:39:19
 *
 */
@Document(collection = "players")
public class Player extends Node implements Serializable {

	private static final long serialVersionUID = -7317388361763958786L;

	private String firstName;
	private String lastName;
	private String nickName;
	@DBRef
	private Country country;
	private String avatarLink;
	private String statement;
	private Double value;
	private Date birthDate;
	private Double height;
	private Double weight;
	private PlayerPositionEnum position;
	@DBRef
	private List<Suspension> suspensions;
	private StatusEnum status;
	private PlayerStateEnum state;
	@DBRef
	private RealCompetitor competitor;
	@DBRef
	private Wage wage;
	@DBRef
	private Set<FinancialHistory> financialHistories;

	public Player() {
	}

	private Player(Builder builder) {
		super(builder.id, builder.creationDate, builder.updateDate);
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.nickName = builder.nickName;
		this.country = builder.country;
		this.avatarLink = builder.avatarLink;
		this.statement = builder.statement;
		this.value = builder.value;
		this.birthDate = builder.birthDate;
		this.height = builder.height;
		this.weight = builder.weight;
		this.position = builder.position;
		this.suspensions = builder.suspensions;
		this.status = builder.status;
		this.state = builder.state;
		this.competitor = builder.competitor;
		this.wage = builder.wage;
		this.financialHistories = builder.financialHistories;
	}

	/**
	 * @return the name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the jerseyName
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * @return the country
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * @return the avatarLink
	 */
	public String getAvatarLink() {
		return avatarLink;
	}

	/**
	 * @return the statement
	 */
	public String getStatement() {
		return statement;
	}

	/**
	 * @return the value
	 */
	public Double getValue() {
		return value;
	}

	/**
	 * @return the birthDate
	 */
	public Date getBirthDate() {
		return birthDate;
	}

	/**
	 * @return the height
	 */
	public Double getHeight() {
		return height;
	}

	/**
	 * @return the weight
	 */
	public Double getWeight() {
		return weight;
	}

	/**
	 * @return the position
	 */
	public PlayerPositionEnum getPosition() {
		return position;
	}

	/**
	 * @return the suspensions
	 */
	public List<Suspension> getSuspensions() {
		return suspensions;
	}

	/**
	 * @return the status
	 */
	public StatusEnum getStatus() {
		return status;
	}

	/**
	 * 
	 * @return the state
	 */
	public PlayerStateEnum getState() {
		return state;
	}

	/**
	 * 
	 * @return the competitor
	 */
	public RealCompetitor getCompetitor() {
		return competitor;
	}

	public Wage getWage() {
		return wage;
	}

	public Set<FinancialHistory> getFinancialHistories() {
		return financialHistories;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((avatarLink == null) ? 0 : avatarLink.hashCode());
		result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
		result = prime * result + ((competitor == null) ? 0 : competitor.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((financialHistories == null) ? 0 : financialHistories.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((height == null) ? 0 : height.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((nickName == null) ? 0 : nickName.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((statement == null) ? 0 : statement.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((suspensions == null) ? 0 : suspensions.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		result = prime * result + ((wage == null) ? 0 : wage.hashCode());
		result = prime * result + ((weight == null) ? 0 : weight.hashCode());
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
		Player other = (Player) obj;
		if (avatarLink == null) {
			if (other.avatarLink != null)
				return false;
		} else if (!avatarLink.equals(other.avatarLink))
			return false;
		if (birthDate == null) {
			if (other.birthDate != null)
				return false;
		} else if (!birthDate.equals(other.birthDate))
			return false;
		if (competitor == null) {
			if (other.competitor != null)
				return false;
		} else if (!competitor.equals(other.competitor))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (financialHistories == null) {
			if (other.financialHistories != null)
				return false;
		} else if (!financialHistories.equals(other.financialHistories))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (height == null) {
			if (other.height != null)
				return false;
		} else if (!height.equals(other.height))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (nickName == null) {
			if (other.nickName != null)
				return false;
		} else if (!nickName.equals(other.nickName))
			return false;
		if (position != other.position)
			return false;
		if (state != other.state)
			return false;
		if (statement == null) {
			if (other.statement != null)
				return false;
		} else if (!statement.equals(other.statement))
			return false;
		if (status != other.status)
			return false;
		if (suspensions == null) {
			if (other.suspensions != null)
				return false;
		} else if (!suspensions.equals(other.suspensions))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		if (wage == null) {
			if (other.wage != null)
				return false;
		} else if (!wage.equals(other.wage))
			return false;
		if (weight == null) {
			if (other.weight != null)
				return false;
		} else if (!weight.equals(other.weight))
			return false;
		return true;
	}

	public static final class Builder {
		private String id;
		private String firstName;
		private String lastName;
		private String nickName;
		private Country country;
		private String avatarLink;
		private String statement;
		private Double value;
		private Date birthDate;
		private Double height;
		private Double weight;
		private PlayerPositionEnum position;
		private List<Suspension> suspensions;
		private StatusEnum status;
		private PlayerStateEnum state;
		private RealCompetitor competitor;
		private Wage wage;
		private Set<FinancialHistory> financialHistories;

		private Date creationDate;
		private Date updateDate;

		private boolean isCreated;

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder firstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public Builder lastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public Builder nickName(String nickName) {
			this.nickName = nickName;
			return this;
		}

		public Builder country(Country country) {
			this.country = country;
			return this;
		}

		public Builder avatarLink(String avatarLink) {
			this.avatarLink = avatarLink;
			return this;
		}

		public Builder statement(String statement) {
			this.statement = statement;
			return this;
		}

		public Builder value(Double value) {
			this.value = value;
			return this;
		}

		public Builder birthDate(Date birthDate) {
			this.birthDate = birthDate;
			return this;
		}

		public Builder height(Double height) {
			this.height = height;
			return this;
		}

		public Builder weight(Double weight) {
			this.weight = weight;
			return this;
		}

		public Builder position(PlayerPositionEnum position) {
			this.position = position;
			return this;
		}

		public Builder suspensions(List<Suspension> suspensions) {
			this.suspensions = suspensions;
			return this;
		}

		public Builder status(StatusEnum status) {
			this.status = status;
			return this;
		}

		public Builder state(PlayerStateEnum state) {
			this.state = state;
			return this;
		}

		public Builder competitor(RealCompetitor competitor) {
			this.competitor = competitor;
			return this;
		}

		public Builder wage(Wage wage) {
			this.wage = wage;
			return this;
		}

		public Builder financialHistories(Set<FinancialHistory> financialHistories) {
			this.financialHistories = financialHistories;
			return this;
		}

		public Builder toCreate() {
			this.isCreated = true;
			return this;
		}

		public Player build() {
			if (isCreated) {
				this.creationDate = new Date();
			} else {
				this.updateDate = new Date();
			}
			return new Player(this);
		}
	}
}
