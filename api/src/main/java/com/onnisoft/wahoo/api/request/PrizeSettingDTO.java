package com.onnisoft.wahoo.api.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.onnisoft.wahoo.model.document.enums.PrizeTypeEnum;

public class PrizeSettingDTO implements Serializable {

	private static final long serialVersionUID = 4234346399239520168L;

	@NotNull
	private String virtualCompetitionId;
	private PrizeTypeEnum type;
	private String name;
	private String sponsorName;
	private String sponsorLogoLink;
	private String prizeImageLink;
	private String info;
	private int value;
	private int numberOfPrizes;
	private int normalPool;
	private int guaranteedPool;
	private int satellitePool;
	private int sponsorPool;

	public PrizeSettingDTO() {
	}

	public PrizeSettingDTO(String virtualCompetitionId, PrizeTypeEnum type, String name, String sponsorName, String sponsorLogoLink, String prizeImageLink,
			String info, int value, int numberOfPrizes, int normalPool, int guaranteedPool, int satellitePool, int sponsorPool) {
		this.virtualCompetitionId = virtualCompetitionId;
		this.type = type;
		this.name = name;
		this.sponsorName = sponsorName;
		this.sponsorLogoLink = sponsorLogoLink;
		this.prizeImageLink = prizeImageLink;
		this.info = info;
		this.value = value;
		this.numberOfPrizes = numberOfPrizes;
		this.normalPool = normalPool;
		this.guaranteedPool = guaranteedPool;
		this.satellitePool = satellitePool;
		this.sponsorPool = sponsorPool;
	}

	/**
	 * @return the virtualCompetitionId
	 */
	public String getVirtualCompetitionId() {
		return virtualCompetitionId;
	}

	/**
	 * 
	 * @return the prize image link
	 */
	public String getPrizeImageLink() {
		return prizeImageLink;
	}

	/**
	 * 
	 * @return info
	 */
	public String getInfo() {
		return info;
	}

	/**
	 * 
	 * @return value of the prize
	 */
	public int getValue() {
		return value;
	}

	/**
	 * 
	 * @return number of prizes
	 */
	public int getNumberOfPrizes() {
		return numberOfPrizes;
	}

	/**
	 * @return the type
	 */
	public PrizeTypeEnum getType() {
		return type;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the sponsorName
	 */
	public String getSponsorName() {
		return sponsorName;
	}

	/**
	 * @return the sponsorLogoLink
	 */
	public String getSponsorLogoLink() {
		return sponsorLogoLink;
	}

	/**
	 * @return the normalPool
	 */
	public int getNormalPool() {
		return normalPool;
	}

	/**
	 * @return the guaranteedPool
	 */
	public int getGuaranteedPool() {
		return guaranteedPool;
	}

	/**
	 * @return the satellitePool
	 */
	public int getSatellitePool() {
		return satellitePool;
	}

	/**
	 * @return the sponsorPool
	 */
	public int getSponsorPool() {
		return sponsorPool;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + guaranteedPool;
		result = prime * result + ((info == null) ? 0 : info.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + normalPool;
		result = prime * result + numberOfPrizes;
		result = prime * result + ((prizeImageLink == null) ? 0 : prizeImageLink.hashCode());
		result = prime * result + satellitePool;
		result = prime * result + ((sponsorLogoLink == null) ? 0 : sponsorLogoLink.hashCode());
		result = prime * result + ((sponsorName == null) ? 0 : sponsorName.hashCode());
		result = prime * result + sponsorPool;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + value;
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
		PrizeSettingDTO other = (PrizeSettingDTO) obj;
		if (guaranteedPool != other.guaranteedPool)
			return false;
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (normalPool != other.normalPool)
			return false;
		if (numberOfPrizes != other.numberOfPrizes)
			return false;
		if (prizeImageLink == null) {
			if (other.prizeImageLink != null)
				return false;
		} else if (!prizeImageLink.equals(other.prizeImageLink))
			return false;
		if (satellitePool != other.satellitePool)
			return false;
		if (sponsorLogoLink == null) {
			if (other.sponsorLogoLink != null)
				return false;
		} else if (!sponsorLogoLink.equals(other.sponsorLogoLink))
			return false;
		if (sponsorName == null) {
			if (other.sponsorName != null)
				return false;
		} else if (!sponsorName.equals(other.sponsorName))
			return false;
		if (sponsorPool != other.sponsorPool)
			return false;
		if (type != other.type)
			return false;
		if (value != other.value)
			return false;
		if (virtualCompetitionId == null) {
			if (other.virtualCompetitionId != null)
				return false;
		} else if (!virtualCompetitionId.equals(other.virtualCompetitionId))
			return false;
		return true;
	}

}
