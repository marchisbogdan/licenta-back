package com.onnisoft.wahoo.subscriber.api.request;

public class PasswordChangeRequestDTO {

	private String currentPassword;
	private String newPassword;
	private String verifyPassword;

	public PasswordChangeRequestDTO() {

	}

	public PasswordChangeRequestDTO(String currentPassword, String newPassword, String verifyPassword) {
		this.currentPassword = currentPassword;
		this.newPassword = newPassword;
		this.verifyPassword = verifyPassword;
	}

	/**
	 * @return the currentPassword
	 */
	public String getCurrentPassword() {
		return currentPassword;
	}

	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * @return the verifyPassword
	 */
	public String getVerifyPassword() {
		return verifyPassword;
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
		result = prime * result + ((currentPassword == null) ? 0 : currentPassword.hashCode());
		result = prime * result + ((newPassword == null) ? 0 : newPassword.hashCode());
		result = prime * result + ((verifyPassword == null) ? 0 : verifyPassword.hashCode());
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
		PasswordChangeRequestDTO other = (PasswordChangeRequestDTO) obj;
		if (currentPassword == null) {
			if (other.currentPassword != null)
				return false;
		} else if (!currentPassword.equals(other.currentPassword))
			return false;
		if (newPassword == null) {
			if (other.newPassword != null)
				return false;
		} else if (!newPassword.equals(other.newPassword))
			return false;
		if (verifyPassword == null) {
			if (other.verifyPassword != null)
				return false;
		} else if (!verifyPassword.equals(other.verifyPassword))
			return false;
		return true;
	}
}
