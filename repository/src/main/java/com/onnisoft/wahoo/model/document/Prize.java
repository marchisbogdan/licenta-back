package com.onnisoft.wahoo.model.document;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.onnisoft.wahoo.model.document.enums.PrizeTypeEnum;

/**
 * Entity that keeps information about the prizes that could be won in a
 * competition.
 *
 * @author mbozesan
 * @date 30 Sep 2016 - 13:48:04
 */
@Document(collection = "prizes")
public class Prize extends Node implements Serializable {

	private static final long serialVersionUID = -7004675982773345322L;

	private PrizeTypeEnum type;
	private String name;
	private String sponsorName;
	private String sponsorLogoLink;
	private String prizeImageLink;
	private String info;
	private Integer value;
	private Integer numberOfPrizes;
	private Integer normalPool;
	private Integer guaranteedPool;
	private Integer satellitePool;
	private Integer sponsorPool;

	public Prize() {

	}

	private Prize(PrizeBuilder builder) {
		super(builder.id, builder.creationDate, builder.updateDate);
		this.type = builder.type;
		this.name = builder.name;
		this.sponsorName = builder.sponsorName;
		this.sponsorLogoLink = builder.sponsorLogoLink;
		this.prizeImageLink = builder.prizeImageLink;
		this.info = builder.info;
		this.value = builder.value;
		this.numberOfPrizes = builder.numberOfPrizes;
		this.normalPool = builder.normalPool;
		this.guaranteedPool = builder.guaranteedPool;
		this.satellitePool = builder.satellitePool;
		this.sponsorPool = builder.sponsorPool;
	}

	/**
	 * 
	 * @return value of the prize
	 */
	public Integer getValue() {
		return value;
	}

	/**
	 * 
	 * @return prize image link
	 */
	public String getPrizeImageLink() {
		return prizeImageLink;
	}

	/**
	 * 
	 * @return info about the prize
	 */
	public String getInfo() {
		return info;
	}

	/**
	 * 
	 * @return the number of prizes
	 */
	public Integer getNumberOfPrizes() {
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
	public Integer getNormalPool() {
		return normalPool;
	}

	/**
	 * @return the guaranteedPool
	 */
	public Integer getGuaranteedPool() {
		return guaranteedPool;
	}

	/**
	 * @return the satellitePool
	 */
	public Integer getSatellitePool() {
		return satellitePool;
	}

	/**
	 * @return the sponsorPool
	 */
	public Integer getSponsorPool() {
		return sponsorPool;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((guaranteedPool == null) ? 0 : guaranteedPool.hashCode());
		result = prime * result + ((info == null) ? 0 : info.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((normalPool == null) ? 0 : normalPool.hashCode());
		result = prime * result + ((numberOfPrizes == null) ? 0 : numberOfPrizes.hashCode());
		result = prime * result + ((prizeImageLink == null) ? 0 : prizeImageLink.hashCode());
		result = prime * result + ((satellitePool == null) ? 0 : satellitePool.hashCode());
		result = prime * result + ((sponsorLogoLink == null) ? 0 : sponsorLogoLink.hashCode());
		result = prime * result + ((sponsorName == null) ? 0 : sponsorName.hashCode());
		result = prime * result + ((sponsorPool == null) ? 0 : sponsorPool.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		Prize other = (Prize) obj;
		if (guaranteedPool == null) {
			if (other.guaranteedPool != null)
				return false;
		} else if (!guaranteedPool.equals(other.guaranteedPool))
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
		if (normalPool == null) {
			if (other.normalPool != null)
				return false;
		} else if (!normalPool.equals(other.normalPool))
			return false;
		if (numberOfPrizes == null) {
			if (other.numberOfPrizes != null)
				return false;
		} else if (!numberOfPrizes.equals(other.numberOfPrizes))
			return false;
		if (prizeImageLink == null) {
			if (other.prizeImageLink != null)
				return false;
		} else if (!prizeImageLink.equals(other.prizeImageLink))
			return false;
		if (satellitePool == null) {
			if (other.satellitePool != null)
				return false;
		} else if (!satellitePool.equals(other.satellitePool))
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
		if (sponsorPool == null) {
			if (other.sponsorPool != null)
				return false;
		} else if (!sponsorPool.equals(other.sponsorPool))
			return false;
		if (type != other.type)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	public static class PrizeBuilder {
		private String id;
		private PrizeTypeEnum type;
		private String name;
		private String sponsorName;
		private String sponsorLogoLink;
		private String prizeImageLink;
		private String info;
		private Integer numberOfPrizes;
		private Integer value;
		private Integer normalPool;
		private Integer guaranteedPool;
		private Integer satellitePool;
		private Integer sponsorPool;

		private boolean isCreated;

		private Date creationDate;
		private Date updateDate;

		public PrizeBuilder() {
		}

		public PrizeBuilder id(String id) {
			this.id = id;
			return this;
		}

		public PrizeBuilder type(PrizeTypeEnum type) {
			this.type = type;
			return this;
		}

		public PrizeBuilder name(String name) {
			this.name = name;
			return this;
		}

		public PrizeBuilder sponsorName(String sponsorName) {
			this.sponsorName = sponsorName;
			return this;
		}

		public PrizeBuilder sponsorLogoLink(String sponsorLogoLink) {
			this.sponsorLogoLink = sponsorLogoLink;
			return this;
		}

		public PrizeBuilder prizeImageLink(String prizeImageLink) {
			this.prizeImageLink = prizeImageLink;
			return this;
		}

		public PrizeBuilder info(String info) {
			this.info = info;
			return this;
		}

		public PrizeBuilder numberOfPrizes(Integer numberOfPrizes) {
			this.numberOfPrizes = numberOfPrizes;
			return this;
		}

		public PrizeBuilder value(Integer value) {
			this.value = value;
			return this;
		}

		public PrizeBuilder normalPool(Integer normalPool) {
			this.normalPool = normalPool;
			return this;
		}

		public PrizeBuilder guaranteedPool(Integer guaranteedPool) {
			this.guaranteedPool = guaranteedPool;
			return this;
		}

		public PrizeBuilder satellitePool(Integer satellitePool) {
			this.satellitePool = satellitePool;
			return this;
		}

		public PrizeBuilder sponsorPool(Integer sponsorPool) {
			this.sponsorPool = sponsorPool;
			return this;
		}

		public PrizeBuilder toCreate() {
			this.isCreated = true;
			return this;
		}

		public Prize build() {
			if (isCreated) {
				this.creationDate = new Date();
			} else {
				this.updateDate = new Date();
			}
			return new Prize(this);
		}
	}

}
