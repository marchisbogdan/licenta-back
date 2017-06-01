package com.onnisoft.wahoo.subscriber.api.request;

import javax.validation.constraints.NotNull;

/**
 * 
 * Authentication Request.
 *
 * @author   mbozesan
 * @date     13 Sep 2016 - 15:18:11
 *
 */
public class AuthenticationRequestDTO {
	
	@NotNull
	private String emailOrUsername;
	@NotNull
	private String password;
	
	/*
	 * Used for json serialization/deserialization.
	 */
	public AuthenticationRequestDTO() {
		
	}
	
	public AuthenticationRequestDTO(String email, String password) {
		this.emailOrUsername = email;
		this.password = password;
	}

	/**
	 * @return the userName
	 */
	public String getEmailOrUsername() {
		return emailOrUsername;
	}

	/**
	 * @return password
	 */
	public String getPassword() {
		return password;
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
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((emailOrUsername == null) ? 0 : emailOrUsername.hashCode());
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
		AuthenticationRequestDTO other = (AuthenticationRequestDTO) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (emailOrUsername == null) {
			if (other.emailOrUsername != null)
				return false;
		} else if (!emailOrUsername.equals(other.emailOrUsername))
			return false;
		return true;
	}	
}