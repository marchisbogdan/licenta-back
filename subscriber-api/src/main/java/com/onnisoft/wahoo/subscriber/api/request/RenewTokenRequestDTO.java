package com.onnisoft.wahoo.subscriber.api.request;

import javax.validation.constraints.NotNull;

public final class RenewTokenRequestDTO {

	@NotNull
	private String emailOrUsername;
	@NotNull
	private String renewTokenId;

	/**
	 * Used for json serialization/deserialization.
	 */
	public RenewTokenRequestDTO() {
	}

	/**
	 * @param email
	 * @param renewTokenId
	 */
	public RenewTokenRequestDTO(String email, String renewTokenId) {
		this.emailOrUsername = email;
		this.renewTokenId = renewTokenId;
	}

	/**
	 * @return the userName
	 */
	public String getEmailOrUsername() {
		return this.emailOrUsername;
	}

	/**
	 * @return the renewTokenId
	 */
	public String getRenewTokenId() {
		return this.renewTokenId;
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
		result = prime * result + ((this.renewTokenId == null) ? 0 : this.renewTokenId.hashCode());
		result = prime * result + ((this.emailOrUsername == null) ? 0 : this.emailOrUsername.hashCode());
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
		RenewTokenRequestDTO other = (RenewTokenRequestDTO) obj;
		if (this.renewTokenId == null) {
			if (other.renewTokenId != null)
				return false;
		} else if (!this.renewTokenId.equals(other.renewTokenId))
			return false;
		if (this.emailOrUsername == null) {
			if (other.emailOrUsername != null)
				return false;
		} else if (!this.emailOrUsername.equals(other.emailOrUsername))
			return false;
		return true;
	}
}
