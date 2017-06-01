package com.onnisoft.wahoo.model.document;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.onnisoft.wahoo.model.document.enums.GenderEnum;
import com.onnisoft.wahoo.model.document.enums.LanguageEnum;

/**
 * 
 * Entity that keeps informations about the profiles. For the current case, a
 * profile contains information additional to the information provided on
 * registration. This information is not mandatory, but might be required for
 * real money prized competitions.
 *
 * @author mbozesan
 * @date 22 Sep 2016 - 11:42:23
 *
 */
@Document(collection = "profiles")
public class Profile extends Node implements Serializable {

	private static final long serialVersionUID = 5946517453006043759L;

	private String address;
	private String zipCode;
	private String city;
	private GenderEnum gender;
	private LanguageEnum language;
	private String statement;
	private String avatarLink;

	public Profile() {

	}

	private Profile(Builder builder) {
		super(builder.id, builder.creationDate, builder.updateDate);
		this.address = builder.address;
		this.zipCode = builder.zipCode;
		this.city = builder.city;
		this.gender = builder.gender;
		this.language = builder.language;
		this.statement = builder.statement;
		this.avatarLink = builder.avatarLink;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * @return the cityId
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return the gender
	 */
	public GenderEnum getGender() {
		return gender;
	}

	/**
	 * @return the language
	 */
	public LanguageEnum getLanguage() {
		return language;
	}

	/**
	 * @return the statusMessage
	 */
	public String getStatement() {
		return statement;
	}

	/**
	 * @return the avatarLink
	 */
	public String getAvatarLink() {
		return avatarLink;
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
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((avatarLink == null) ? 0 : avatarLink.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((language == null) ? 0 : language.hashCode());
		result = prime * result + ((statement == null) ? 0 : statement.hashCode());
		result = prime * result + ((zipCode == null) ? 0 : zipCode.hashCode());
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
		Profile other = (Profile) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (avatarLink == null) {
			if (other.avatarLink != null)
				return false;
		} else if (!avatarLink.equals(other.avatarLink))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (statement == null) {
			if (other.statement != null)
				return false;
		} else if (!statement.equals(other.statement))
			return false;
		if (zipCode == null) {
			if (other.zipCode != null)
				return false;
		} else if (!zipCode.equals(other.zipCode))
			return false;
		return true;
	}

	public static final class Builder {
		private String id;
		private String address;
		private String zipCode;
		private String city;
		private GenderEnum gender;
		private LanguageEnum language;
		private String statement;
		private String avatarLink;

		private boolean isCreated;

		private Date creationDate;
		private Date updateDate;

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder address(String address) {
			this.address = address;
			return this;
		}

		public Builder zipCode(String zipCode) {
			this.zipCode = zipCode;
			return this;
		}

		public Builder city(String city) {
			this.city = city;
			return this;
		}

		public Builder gender(GenderEnum gender) {
			this.gender = gender;
			return this;
		}

		public Builder language(LanguageEnum language) {
			this.language = language;
			return this;
		}

		public Builder statement(String statement) {
			this.statement = statement;
			return this;
		}

		public Builder avatarLink(String avatarLink) {
			this.avatarLink = avatarLink;
			return this;
		}

		public Builder toCreate() {
			this.isCreated = true;
			return this;
		}

		public Profile builder() {
			if (isCreated) {
				this.creationDate = new Date();
			} else {
				this.updateDate = new Date();
			}
			return new Profile(this);
		}
	}
}
