package com.onnisoft.ssp.validation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.onnisoft.validation.UserNameValidator;
import com.onnisoft.validation.exception.ValidationException;

@ContextConfiguration(locations = { "classpath:wahoo-test.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class UserNameValidatorTest {

	@Autowired
	UserNameValidator validator;

	private static final String EMAIL = "john.doe@wahoo.com";
	private static final String UNAME = "johndoe";

	@Test
	public void testUserNameValidatorOnEmailOnSucces() throws ValidationException {
		validator.validate(EMAIL);
	}

	@Test
	public void testUserNameValidatorOnUserNameOnSucces() throws ValidationException {
		validator.validate(UNAME);
	}

	@Test(expected = ValidationException.class)
	public void testUserNameValidatorOnFail() throws ValidationException {
		validator.validate("/?!#$%^$|:{}[]()_-+=*");
	}
}
