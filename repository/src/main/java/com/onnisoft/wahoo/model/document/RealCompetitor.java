package com.onnisoft.wahoo.model.document;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.onnisoft.wahoo.model.document.enums.StatusEnum;

/**
 * 
 * Entity that keeps information about clubs. A club is a real life team.
 *
 * @author mbozesan
 * @date 30 Sep 2016 - 13:07:06
 *
 */
@Document(collection = "real-competitors")
public class RealCompetitor extends Node implements Serializable {

	private static final long serialVersionUID = -4792356289872383554L;

	private String name;
	@DBRef
	private Country country;
	private String logoUrl;
	private String shirtUrl;
	private String clubUrl;
	private String statement;
	private StatusEnum status;
	@DBRef
	private Set<Logbook> logbooks;

	public RealCompetitor() {
	}

	private RealCompetitor(Builder builder) {
		super(builder.id, builder.creationDate, builder.updateDate);
		this.name = builder.name;
		this.country = builder.country;
		this.logoUrl = builder.logoUrl;
		this.shirtUrl = builder.shirtUrl;
		this.clubUrl = builder.clubUrl;
		this.statement = builder.statement;
		this.status = builder.status;
		this.logbooks = builder.logbooks;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the country
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * 
	 * @return the logo url
	 */
	public String getLogoUrl() {
		return logoUrl;
	}

	/**
	 * 
	 * @return the shirt url
	 */
	public String getShirtUrl() {
		return shirtUrl;
	}

	/**
	 * 
	 * @return the club url
	 */
	public String getClubUrl() {
		return clubUrl;
	}

	/**
	 * @return the statement
	 */
	public String getStatement() {
		return statement;
	}

	/**
	 * @return the status
	 */
	public StatusEnum getStatus() {
		return status;
	}

	/**
	 * 
	 * @return the logbooks
	 */
	public Set<Logbook> getLogbooks() {
		return logbooks;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((clubUrl == null) ? 0 : clubUrl.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((logbooks == null) ? 0 : logbooks.hashCode());
		result = prime * result + ((logoUrl == null) ? 0 : logoUrl.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((shirtUrl == null) ? 0 : shirtUrl.hashCode());
		result = prime * result + ((statement == null) ? 0 : statement.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		RealCompetitor other = (RealCompetitor) obj;
		if (clubUrl == null) {
			if (other.clubUrl != null)
				return false;
		} else if (!clubUrl.equals(other.clubUrl))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (logbooks == null) {
			if (other.logbooks != null)
				return false;
		} else if (!logbooks.equals(other.logbooks))
			return false;
		if (logoUrl == null) {
			if (other.logoUrl != null)
				return false;
		} else if (!logoUrl.equals(other.logoUrl))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (shirtUrl == null) {
			if (other.shirtUrl != null)
				return false;
		} else if (!shirtUrl.equals(other.shirtUrl))
			return false;
		if (statement == null) {
			if (other.statement != null)
				return false;
		} else if (!statement.equals(other.statement))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	public static final class Builder {
		private String id;
		private String name;
		private Country country;
		private String logoUrl;
		private String shirtUrl;
		private String clubUrl;
		private String statement;
		private StatusEnum status;
		private Set<Logbook> logbooks;

		private boolean isCreated;

		private Date creationDate;
		private Date updateDate;

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder country(Country country) {
			this.country = country;
			return this;
		}

		public Builder logoUrl(String logoUrl) {
			this.logoUrl = logoUrl;
			return this;
		}

		public Builder shirtUrl(String shirtUrl) {
			this.shirtUrl = shirtUrl;
			return this;
		}

		public Builder clubUrl(String clubUrl) {
			this.clubUrl = clubUrl;
			return this;
		}

		public Builder statement(String statement) {
			this.statement = statement;
			return this;
		}

		public Builder status(StatusEnum status) {
			this.status = status;
			return this;
		}

		public Builder logbooks(Set<Logbook> logbooks) {
			this.logbooks = logbooks;
			return this;
		}

		public Builder toCreate() {
			this.isCreated = true;
			return this;
		}

		public RealCompetitor build() {
			if (isCreated) {
				this.creationDate = new Date();
			} else {
				this.updateDate = new Date();
			}
			return new RealCompetitor(this);
		}
	}
}
