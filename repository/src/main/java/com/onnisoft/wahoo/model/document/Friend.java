package com.onnisoft.wahoo.model.document;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
 * Entity that keeps information about the friend list.
 *
 * @author mbozesan
 * @date 23 Sep 2016 - 16:49:45
 *
 */
@Document(collection = "friends")
public class Friend implements Serializable {

	private static final long serialVersionUID = 8431784538491049742L;

	@Id
	private String id;
	@DBRef
	private Subscriber subscriber;
	@DBRef
	private Subscriber friend;

	public Friend() {
	}

	public Friend(String id, Subscriber subscriber, Subscriber friend) {
		this.id = id;
		this.subscriber = subscriber;
		this.friend = friend;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the subscriberId
	 */
	public Subscriber getSubscriber() {
		return subscriber;
	}

	/**
	 * @return the friendId
	 */
	public Subscriber getFriend() {
		return friend;
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
		result = prime * result + ((friend == null) ? 0 : friend.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Friend other = (Friend) obj;
		if (friend == null) {
			if (other.friend != null)
				return false;
		} else if (!friend.equals(other.friend))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (subscriber == null) {
			if (other.subscriber != null)
				return false;
		} else if (!subscriber.equals(other.subscriber))
			return false;
		return true;
	}
}
