package com.onnisoft.wahoo.model.document.enums;

/**
 * Authenticated subscriber status.
 *
 * @author mbozesan
 * @date Mar 2, 2016 - 3:24:12 PM
 *
 */
public enum SubscriberStatusEnum {

	/**
	 * Newly created account, that was not yet activated *
	 */
	INIT,
	/**
	 * The status of an account that was activated. *
	 */
	ACTIVE,
	/**
	 * The status of an account that was suspended. *
	 */
	SUSPENDED,
	/**
	 * The status of an account that was deleted. *
	 */
	DELETED;
}
