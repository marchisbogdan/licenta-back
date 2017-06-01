package com.onnisoft.wahoo.model.document;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.onnisoft.wahoo.model.document.enums.DeviceTypeEnum;

/**
 * Entity that keeps information about the supported devices of a particular
 * user.
 *
 * @author alexandru.mos
 * @date Jun 14, 2016 - 2:56:06 PM
 *
 */
@Document(collection = "subscriber-devices")
public final class SubscriberDevice implements Serializable {

	private static final long serialVersionUID = -1575783698388168619L;

	@Id
	private String renewTokenId;

	private DeviceTypeEnum deviceType;
	private String userAgent;
	private String deviceToken;

	@DBRef
	private Subscriber subscriber;

	public SubscriberDevice() {

	}

	/**
	 * @param renewTokenId
	 * @param deviceType
	 * @param userAgent
	 * @param deviceToken
	 * @param subscriber
	 */
	private SubscriberDevice(SubscriberDeviceBuilder builder) {
		this.renewTokenId = builder.renewTokenId;
		this.deviceType = builder.deviceType;
		this.userAgent = builder.userAgent;
		this.deviceToken = builder.deviceToken;
		this.subscriber = builder.subscriber;
	}

	/**
	 * @return the renewTokenId
	 */
	public String getRenewTokenId() {
		return this.renewTokenId;
	}

	/**
	 * @return the deviceType
	 */
	public DeviceTypeEnum getDeviceType() {
		return this.deviceType;
	}

	/**
	 * @return the userAgent
	 */
	public String getUserAgent() {
		return this.userAgent;
	}

	/**
	 * @return the deviceToken
	 */
	public String getDeviceToken() {
		return this.deviceToken;
	}

	/**
	 * @return the subscriber
	 */
	public Subscriber getSubscriber() {
		return this.subscriber;
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
		result = prime * result + ((this.deviceToken == null) ? 0 : this.deviceToken.hashCode());
		result = prime * result + ((this.deviceType == null) ? 0 : this.deviceType.hashCode());
		result = prime * result + ((this.renewTokenId == null) ? 0 : this.renewTokenId.hashCode());
		result = prime * result + ((this.subscriber == null) ? 0 : this.subscriber.hashCode());
		result = prime * result + ((this.userAgent == null) ? 0 : this.userAgent.hashCode());
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
		SubscriberDevice other = (SubscriberDevice) obj;
		if (this.deviceToken == null) {
			if (other.deviceToken != null)
				return false;
		} else if (!this.deviceToken.equals(other.deviceToken))
			return false;
		if (this.deviceType != other.deviceType)
			return false;
		if (this.renewTokenId == null) {
			if (other.renewTokenId != null)
				return false;
		} else if (!this.renewTokenId.equals(other.renewTokenId))
			return false;
		if (this.subscriber == null) {
			if (other.subscriber != null)
				return false;
		} else if (!this.subscriber.equals(other.subscriber))
			return false;
		if (this.userAgent == null) {
			if (other.userAgent != null)
				return false;
		} else if (!this.userAgent.equals(other.userAgent))
			return false;
		return true;
	}

	public static class SubscriberDeviceBuilder {
		private String renewTokenId;
		private DeviceTypeEnum deviceType;
		private String userAgent;
		private String deviceToken;
		private Subscriber subscriber;

		public SubscriberDeviceBuilder() {

		}

		public SubscriberDeviceBuilder renewTokenId(String renewTokenId) {
			this.renewTokenId = renewTokenId;
			return this;
		}

		public SubscriberDeviceBuilder deviceType(DeviceTypeEnum deviceType) {
			this.deviceType = deviceType != null ? deviceType : DeviceTypeEnum.UNKNOWN;
			return this;
		}

		public SubscriberDeviceBuilder userAgent(String userAgent) {
			this.userAgent = userAgent;
			return this;
		}

		public SubscriberDeviceBuilder deviceToken(String deviceToken) {
			this.deviceToken = deviceToken;
			return this;
		}

		public SubscriberDeviceBuilder subscriber(Subscriber subscriber) {
			this.subscriber = subscriber;
			return this;
		}

		public SubscriberDevice build() {
			return new SubscriberDevice(this);
		}
	}
}
