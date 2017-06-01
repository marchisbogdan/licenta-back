package com.onnisoft.wahoo.api.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * 
 * Login on ssp to get countries.
 *
 * @author mbozesan
 * @date 20 Sep 2016 - 17:28:39
 *
 */
public class MakoRemoteAuthenticationRequestDTO implements Serializable {

	private static final long serialVersionUID = -4224150681744452281L;

	@NotNull
	private String userName;
	@NotNull
	private String password;

	public MakoRemoteAuthenticationRequestDTO() {

	}

	public MakoRemoteAuthenticationRequestDTO(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{\r\n  \"userName\": \"" + userName + "\",\r\n  \"password\": \"" + password + "\"\r\n}";
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
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
		MakoRemoteAuthenticationRequestDTO other = (MakoRemoteAuthenticationRequestDTO) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
}
