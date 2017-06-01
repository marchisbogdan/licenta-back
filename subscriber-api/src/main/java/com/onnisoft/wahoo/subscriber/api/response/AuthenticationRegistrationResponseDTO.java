package com.onnisoft.wahoo.subscriber.api.response;

import com.onnisoft.wahoo.model.document.Subscriber;

/**
 * 
 * Authentication/Registration Response.
 *
 * @author   mbozesan
 * @date     13 Sep 2016 - 11:27:32
 *
 */
public class AuthenticationRegistrationResponseDTO {
	
	private String jwtToken;
	private String renewTokenId;
	private Subscriber subscriber;
	
	/**
	 * Used for json serialization/deserialization
	 */
	public AuthenticationRegistrationResponseDTO() {
		
	}
	
	/**
	 * 
	 * @param jwtToken
	 * @param renewTokenId
	 * @param subscriber
	 */
	public AuthenticationRegistrationResponseDTO(String jwtToken, String renewTokenId, Subscriber subscriber) {
		this.jwtToken = jwtToken;
		this.renewTokenId = renewTokenId;
		this.subscriber = subscriber;
	}

	/**
	 * @return the jwtToken
	 */
	public String getJwtToken() {
		return jwtToken;
	}

	/**
	 * @return the renewTokenId
	 */
	public String getRenewTokenId() {
		return renewTokenId;
	}

	
	/**
	 * @return the subscriber
	 */
	public Subscriber getSubscriber() {
		return subscriber;
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
		result = prime * result + ((jwtToken == null) ? 0 : jwtToken.hashCode());
		result = prime * result + ((renewTokenId == null) ? 0 : renewTokenId.hashCode());
		result = prime * result + ((subscriber == null) ? 0 : subscriber.hashCode());
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
		AuthenticationRegistrationResponseDTO other = (AuthenticationRegistrationResponseDTO) obj;
		if (jwtToken == null) {
			if (other.jwtToken != null)
				return false;
		} else if (!jwtToken.equals(other.jwtToken))
			return false;
		if (renewTokenId == null) {
			if (other.renewTokenId != null)
				return false;
		} else if (!renewTokenId.equals(other.renewTokenId))
			return false;
		if (subscriber == null) {
			if (other.subscriber != null)
				return false;
		} else if (!subscriber.equals(other.subscriber))
			return false;
		return true;
	}
}
