package com.onnisoft.wahoo.subscriber.api.request;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import javax.validation.constraints.NotNull;

import com.onnisoft.wahoo.model.document.enums.SubscriberRoleEnum;

/**
 * @author alexandru.mos
 */
public class RegistrationRequestDTO implements Serializable {

	private static final long serialVersionUID = 4671207444490969311L;

	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	@NotNull
	private String userName;
	@NotNull
	private String email;
	@NotNull
	private String password;

	private String countryId;

	private String partnerId;

	private String birthDate;

	private boolean agreedToTermsAndConditions;

	private SubscriberRoleEnum role;

	public static final SimpleDateFormat format = new SimpleDateFormat("MM-DD-YYYY");

	/**
	 * Used for json serialization/deserialization.
	 */
	public RegistrationRequestDTO() {
	}

	public RegistrationRequestDTO(String id, String email, String userName, String firstName, String lastName, String password, String countryId,
			String partnerId, String birthDate, boolean agreedToTermsAndConditions, SubscriberRoleEnum role) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.countryId = countryId;
		this.partnerId = partnerId;
		this.birthDate = birthDate;
		this.agreedToTermsAndConditions = agreedToTermsAndConditions;
		this.role = role;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getUserName() {
		return userName;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getCountryId() {
		return countryId;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public boolean isAgreedToTermsAndConditions() {
		return agreedToTermsAndConditions;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public SubscriberRoleEnum getRole() {
		return role;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (agreedToTermsAndConditions ? 1231 : 1237);
		result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
		result = prime * result + ((countryId == null) ? 0 : countryId.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((partnerId == null) ? 0 : partnerId.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
		RegistrationRequestDTO other = (RegistrationRequestDTO) obj;
		if (agreedToTermsAndConditions != other.agreedToTermsAndConditions)
			return false;
		if (birthDate == null) {
			if (other.birthDate != null)
				return false;
		} else if (!birthDate.equals(other.birthDate))
			return false;
		if (countryId == null) {
			if (other.countryId != null)
				return false;
		} else if (!countryId.equals(other.countryId))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (partnerId == null) {
			if (other.partnerId != null)
				return false;
		} else if (!partnerId.equals(other.partnerId))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (role != other.role)
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

}
