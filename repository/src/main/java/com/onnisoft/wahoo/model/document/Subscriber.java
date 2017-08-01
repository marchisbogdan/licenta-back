package com.onnisoft.wahoo.model.document;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onnisoft.wahoo.model.document.enums.SubscriberRoleEnum;
import com.onnisoft.wahoo.model.document.enums.SubscriberStatusEnum;

@Document(collection = "subscribers")
public final class Subscriber extends Node implements Serializable {

	private static final long serialVersionUID = 3994770512020523004L;

	private String firstName;
	private String lastName;
	private String userName;
	private String email;
	@JsonIgnore
	private String password;
	private Date birthDate;
	@DBRef
	private Country country;
	private SubscriberRoleEnum role;
	private SubscriberStatusEnum status;
	@DBRef
	private Profile profile;
	@DBRef
	private Subscriber partner;
	private Date tokenExpirationDate;

	public Subscriber() {
	}

	private Subscriber(SubscriberBuilder builder) {
		super(builder.id, builder.creationDate, builder.updateDate);
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.userName = builder.userName;
		this.email = builder.email;
		this.password = builder.password;
		this.birthDate = builder.birthDate;
		this.country = builder.country;
		this.role = builder.role;
		this.status = builder.status;
		this.profile = builder.profile;
		this.partner = builder.partner;
		this.tokenExpirationDate = builder.tokenExpirationDate;
	}

	/***
	 * 
	 * @return partner
	 */
	public Subscriber getPartner() {
		return partner;
	}

	/**
	 * 
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * 
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * 
	 * @return userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 
	 * @return country
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * 
	 * @return birthDate
	 */
	public Date getBirthDate() {
		return birthDate;
	}

	/**
	 * 
	 * @return role
	 */
	public SubscriberRoleEnum getRole() {
		return role;
	}

	/**
	 * 
	 * @return status
	 */
	public SubscriberStatusEnum getStatus() {
		return status;
	}

	/**
	 * @return the profile
	 */
	public Profile getProfile() {
		return profile;
	}

	/**
	 * 
	 * @return the token expiration date and time
	 */
	public Date getTokenExpirationDate() {
		return tokenExpirationDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((partner == null) ? 0 : partner.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((profile == null) ? 0 : profile.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((tokenExpirationDate == null) ? 0 : tokenExpirationDate.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
		Subscriber other = (Subscriber) obj;
		if (birthDate == null) {
			if (other.birthDate != null)
				return false;
		} else if (!birthDate.equals(other.birthDate))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (partner == null) {
			if (other.partner != null)
				return false;
		} else if (!partner.equals(other.partner))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (profile == null) {
			if (other.profile != null)
				return false;
		} else if (!profile.equals(other.profile))
			return false;
		if (role != other.role)
			return false;
		if (status != other.status)
			return false;
		if (tokenExpirationDate == null) {
			if (other.tokenExpirationDate != null)
				return false;
		} else if (!tokenExpirationDate.equals(other.tokenExpirationDate))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Subscriber [firstName=" + firstName + ", lastName=" + lastName + ", userName=" + userName + ", email=" + email + ", password=" + password
				+ ", birthDate=" + birthDate + ", country=" + country + ", role=" + role + ", status=" + status + ", profile=" + profile + ", partner="
				+ partner + ", tokenExpirationDate=" + tokenExpirationDate + "]";
	}

	public static final class SubscriberBuilder {
		private String id;
		private String firstName;
		private String lastName;
		private String userName;
		private String email;
		private String password;
		private Date birthDate;
		private Country country;
		private SubscriberRoleEnum role;
		private SubscriberStatusEnum status;
		private Profile profile;
		private Subscriber partner;
		private Date tokenExpirationDate;

		private Date creationDate;
		private Date updateDate;

		private boolean isCreated;

		public SubscriberBuilder() {

		}

		/**
		 * called when the object is created for the first time to be inserted
		 * in the DB
		 * 
		 * @return
		 */
		public SubscriberBuilder toCreate() {
			this.isCreated = true;
			return this;
		}

		public SubscriberBuilder id(String id) {
			this.id = id;
			return this;
		}

		public SubscriberBuilder firstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public SubscriberBuilder lastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public SubscriberBuilder userName(String userName) {
			this.userName = userName;
			return this;
		}

		public SubscriberBuilder email(String email) {
			this.email = email;
			return this;
		}

		public SubscriberBuilder password(String password) {
			this.password = password;
			return this;
		}

		public SubscriberBuilder birthDate(Date birthDate) {
			this.birthDate = birthDate;
			return this;
		}

		public SubscriberBuilder country(Country country) {
			this.country = country;
			return this;
		}

		public SubscriberBuilder role(SubscriberRoleEnum role) {
			this.role = role;
			return this;
		}

		public SubscriberBuilder status(SubscriberStatusEnum status) {
			this.status = status;
			return this;
		}

		public SubscriberBuilder profile(Profile profile) {
			this.profile = profile;
			return this;
		}

		public SubscriberBuilder partner(Subscriber partner) {
			this.partner = partner;
			return this;
		}

		public SubscriberBuilder tokenExpirationDate(Date tokenExpirationDate) {
			this.tokenExpirationDate = tokenExpirationDate;
			return this;
		}

		public Subscriber build() {
			if (isCreated) {
				this.creationDate = new Date();
			} else {
				this.updateDate = new Date();
			}
			return new Subscriber(this);
		}
	}
}
