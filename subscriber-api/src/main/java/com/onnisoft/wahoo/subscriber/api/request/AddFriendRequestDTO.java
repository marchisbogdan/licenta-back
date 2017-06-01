package com.onnisoft.wahoo.subscriber.api.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.onnisoft.wahoo.model.document.Subscriber;

/**
 * 
 * Add friend request.
 *
 * @author mbozesan
 * @date 20 Oct 2016 - 15:12:15
 *
 */
public class AddFriendRequestDTO implements Serializable {

	private static final long serialVersionUID = -9054491762899841892L;

	@NotNull
	private Subscriber friend;

	public AddFriendRequestDTO() {
	}

	public AddFriendRequestDTO(Subscriber friend) {
		this.friend = friend;
	}

	public Subscriber getFriend() {
		return friend;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((friend == null) ? 0 : friend.hashCode());
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
		AddFriendRequestDTO other = (AddFriendRequestDTO) obj;
		if (!friend.equals(other.friend))
			return false;
		return true;
	}
}
