package com.onnisoft.ssp.validation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.onnisoft.validation.RegistrationValidator;
import com.onnisoft.validation.exception.ValidationException;
import com.onnisoft.wahoo.model.document.enums.SubscriberRoleEnum;
import com.onnisoft.wahoo.subscriber.api.request.RegistrationRequestDTO;

@ContextConfiguration(locations = { "classpath:wahoo-test.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class RegistrationValidatorTest {

	@Autowired
	private RegistrationValidator validator;

	private RegistrationRequestDTO dto;

	private static final String EMAIL = "john.doe@wahoo.com";
	private static final String IVEMAIL = "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee";
	private static final String UNAME = "johndoe92";
	private static final String FNAME = "John";
	private static final String LNAME = "Doe";
	private static final String PASS = "abc123";
	private static final String COUNTRYID = "2gvkh32h";
	private static final String PARTNERID = "3gvkh32h";
	private static final String BDATE = "1992-06-26";

	@Test
	public void testValidateSucces() throws ValidationException {
		dto = new RegistrationRequestDTO(null, EMAIL, UNAME, FNAME, LNAME, PASS, COUNTRYID, PARTNERID, BDATE, true, SubscriberRoleEnum.USER);
		this.validator.validate(dto);
	}

	@Test(expected = ValidationException.class)
	public void testValidateNullRequestFail() throws ValidationException {
		this.validator.validate(dto);
	}

	@Test(expected = ValidationException.class)
	public void testValidateUserNameTooShortFail() throws ValidationException {
		dto = new RegistrationRequestDTO(null, EMAIL, "u", FNAME, LNAME, PASS, COUNTRYID, PARTNERID, BDATE, true, SubscriberRoleEnum.USER);
		this.validator.validate(dto);
	}

	@Test(expected = ValidationException.class)
	public void testValidationUserNameTooLongFail() throws ValidationException {
		dto = new RegistrationRequestDTO(null, EMAIL, "uuuuuuuuuuuuuuuuuuuuuuuuuu", FNAME, LNAME, PASS, COUNTRYID, PARTNERID, BDATE, true,
				SubscriberRoleEnum.USER);
		this.validator.validate(dto);
	}

	@Test(expected = ValidationException.class)
	public void testValidationUserNameNullFail() throws ValidationException {
		dto = new RegistrationRequestDTO(null, EMAIL, null, FNAME, LNAME, PASS, COUNTRYID, PARTNERID, BDATE, true, SubscriberRoleEnum.USER);
		this.validator.validate(dto);
	}

	@Test(expected = ValidationException.class)
	public void testValidatEmailTooShortFail() throws ValidationException {
		dto = new RegistrationRequestDTO(null, "e", UNAME, FNAME, LNAME, PASS, COUNTRYID, PARTNERID, BDATE, true, SubscriberRoleEnum.USER);
		this.validator.validate(dto);
	}

	@Test(expected = ValidationException.class)
	public void testValidationEmailTooLongFail() throws ValidationException {
		dto = new RegistrationRequestDTO(null, IVEMAIL, UNAME, FNAME, LNAME, PASS, COUNTRYID, PARTNERID, BDATE, true, SubscriberRoleEnum.USER);
		this.validator.validate(dto);
	}

	@Test(expected = ValidationException.class)
	public void testValidationEmailNullFail() throws ValidationException {
		dto = new RegistrationRequestDTO(null, null, UNAME, FNAME, LNAME, PASS, COUNTRYID, PARTNERID, BDATE, true, SubscriberRoleEnum.USER);
		this.validator.validate(dto);
	}

	@Test(expected = ValidationException.class)
	public void testValidationUnAgreedFail() throws ValidationException {
		dto = new RegistrationRequestDTO(null, EMAIL, UNAME, FNAME, LNAME, PASS, COUNTRYID, PARTNERID, BDATE, false, SubscriberRoleEnum.USER);
		this.validator.validate(dto);
	}
}
