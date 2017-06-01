package com.onnisoft.wahoo.api.request;

import java.io.Serializable;

public class StatisticsFilterRequestDTO implements Serializable {

	private static final long serialVersionUID = 5511151573092578177L;
	private Integer year;
	private Integer month;
	private Integer day;
	private Integer hour;
	private String countryId;
	private String partnerId;

	public StatisticsFilterRequestDTO() {

	}

	public StatisticsFilterRequestDTO(Integer year, Integer month, Integer day, Integer hour, String countryId, String partnerId) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.countryId = countryId;
		this.partnerId = partnerId;
	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	public int getHour() {
		return hour;
	}

	public String getCountryId() {
		return countryId;
	}

	public String getPartnerId() {
		return partnerId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((countryId == null) ? 0 : countryId.hashCode());
		result = prime * result + day;
		result = prime * result + hour;
		result = prime * result + month;
		result = prime * result + ((partnerId == null) ? 0 : partnerId.hashCode());
		result = prime * result + year;
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
		StatisticsFilterRequestDTO other = (StatisticsFilterRequestDTO) obj;
		if (countryId == null) {
			if (other.countryId != null)
				return false;
		} else if (!countryId.equals(other.countryId))
			return false;
		if (day != other.day)
			return false;
		if (hour != other.hour)
			return false;
		if (month != other.month)
			return false;
		if (partnerId == null) {
			if (other.partnerId != null)
				return false;
		} else if (!partnerId.equals(other.partnerId))
			return false;
		if (year != other.year)
			return false;
		return true;
	}
}
