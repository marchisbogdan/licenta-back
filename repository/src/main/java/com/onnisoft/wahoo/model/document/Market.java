package com.onnisoft.wahoo.model.document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.CollectionUtils;

import com.onnisoft.wahoo.model.document.enums.GenderEnum;

@Document(collection = "market")
public class Market extends Node implements Serializable {

	private static final long serialVersionUID = -7998109885054482475L;

	private String name;
	private Boolean countryRestricted;
	private List<Country> countries;
	private Boolean partnerRestricted;
	@DBRef
	private Subscriber partner;
	private GenderEnum targetGender;
	// TODO: targetInterests should be replaced with an object when it will be
	// clear what it has to contain.
	private String targetInterests;
	private Integer targetAge;

	public Market() {
	}

	private Market(Builder builder) {
		super(builder.id, builder.creationDate, builder.updateDate);
		this.name = builder.name;
		this.countryRestricted = builder.countryRestricted;
		this.countries = builder.countries;
		this.partnerRestricted = builder.partnerRestricted;
		this.partner = builder.partner;
		this.targetGender = builder.targetGender;
		this.targetInterests = builder.targetInterests;
		this.targetAge = builder.targetAge;
	}

	public String getName() {
		return name;
	}

	public Boolean getCountryRestricted() {
		return countryRestricted;
	}

	public List<Country> getCountries() {
		return countries;
	}

	public Boolean getPartnerRestricted() {
		return partnerRestricted;
	}

	public Subscriber getPartner() {
		return partner;
	}

	public GenderEnum getTargetGender() {
		return targetGender;
	}

	public String getTargetInterests() {
		return targetInterests;
	}

	public Integer getTargetAge() {
		return targetAge;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((countries == null) ? 0 : countries.hashCode());
		result = prime * result + ((countryRestricted == null) ? 0 : countryRestricted.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((partner == null) ? 0 : partner.hashCode());
		result = prime * result + ((partnerRestricted == null) ? 0 : partnerRestricted.hashCode());
		result = prime * result + ((targetAge == null) ? 0 : targetAge.hashCode());
		result = prime * result + ((targetGender == null) ? 0 : targetGender.hashCode());
		result = prime * result + ((targetInterests == null) ? 0 : targetInterests.hashCode());
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
		Market other = (Market) obj;
		if (countries == null) {
			if (other.countries != null)
				return false;
		} else if (!countries.equals(other.countries))
			return false;
		if (countryRestricted == null) {
			if (other.countryRestricted != null)
				return false;
		} else if (!countryRestricted.equals(other.countryRestricted))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (partner == null) {
			if (other.partner != null)
				return false;
		} else if (!partner.equals(other.partner))
			return false;
		if (partnerRestricted == null) {
			if (other.partnerRestricted != null)
				return false;
		} else if (!partnerRestricted.equals(other.partnerRestricted))
			return false;
		if (targetAge == null) {
			if (other.targetAge != null)
				return false;
		} else if (!targetAge.equals(other.targetAge))
			return false;
		if (targetGender != other.targetGender)
			return false;
		if (targetInterests == null) {
			if (other.targetInterests != null)
				return false;
		} else if (!targetInterests.equals(other.targetInterests))
			return false;
		return true;
	}

	public static final class Builder {
		private String id;
		private String name;
		private Boolean countryRestricted;
		private List<Country> countries;
		private Boolean partnerRestricted;
		private Subscriber partner;
		private GenderEnum targetGender;
		private String targetInterests;
		private Integer targetAge;

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

		public Builder countries(List<Country> countries) {
			this.countryRestricted = Boolean.TRUE;
			this.countries = countries;
			return this;
		}

		public Builder partner(Subscriber partner) {
			this.partnerRestricted = Boolean.TRUE;
			this.partner = partner;
			return this;
		}

		public Builder countryRestricted(boolean countryRestricted) {
			this.countryRestricted = countryRestricted;
			return this;
		}

		public Builder partnerRestricted(boolean partnerRestricted) {
			this.partnerRestricted = partnerRestricted;
			return this;
		}

		public Builder targetGender(GenderEnum targetGender) {
			this.targetGender = targetGender;
			return this;
		}

		public Builder targetInterests(String targetInterests) {
			this.targetInterests = targetInterests;
			return this;
		}

		public Builder targetAge(int targetAge) {
			this.targetAge = targetAge;
			return this;
		}

		public Builder toCreate() {
			this.isCreated = true;
			return this;
		}

		public Market build() {
			if (CollectionUtils.isEmpty(countries)) {
				this.countryRestricted = Boolean.FALSE;
			}
			if (partner == null) {
				this.partnerRestricted = Boolean.FALSE;
			}
			if (isCreated) {
				this.creationDate = new Date();
			} else {
				this.updateDate = new Date();
			}
			return new Market(this);
		}
	}
}
