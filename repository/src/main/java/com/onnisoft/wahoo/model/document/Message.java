package com.onnisoft.wahoo.model.document;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
 * Entity that keeps informations about the messages. This entity will be used
 * in the chat functionality.
 *
 * @author mbozesan
 * @date 23 Sep 2016 - 16:48:44
 *
 */
@Document(collection = "messages")
public class Message implements Serializable {

	private static final long serialVersionUID = -8531901179923383041L;

	@Id
	private String id;
	private String text;
	@DBRef
	private Subscriber sender;
	@DBRef
	private Subscriber receiver;
	private Date dateTimeReceived;
	private String aditionalContentId;

	public Message() {
	}

	public Message(String id, String text, Subscriber sender, Subscriber receiver, Date dateTimeReceived, String aditionalContentId) {
		this.id = id;
		this.text = text;
		this.sender = sender;
		this.receiver = receiver;
		this.dateTimeReceived = dateTimeReceived;
		this.aditionalContentId = aditionalContentId;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @return the senderId
	 */
	public Subscriber getSenderId() {
		return sender;
	}

	/**
	 * @return the receiverId
	 */
	public Subscriber getReceiverId() {
		return receiver;
	}

	/**
	 * @return the dateTimeReceived
	 */
	public Date getDateTimeReceived() {
		return dateTimeReceived;
	}

	/**
	 * @return the aditionalContentId
	 */
	public String getAditionalContentId() {
		return aditionalContentId;
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
		result = prime * result + ((aditionalContentId == null) ? 0 : aditionalContentId.hashCode());
		result = prime * result + ((dateTimeReceived == null) ? 0 : dateTimeReceived.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((receiver == null) ? 0 : receiver.hashCode());
		result = prime * result + ((sender == null) ? 0 : sender.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		Message other = (Message) obj;
		if (aditionalContentId == null) {
			if (other.aditionalContentId != null)
				return false;
		} else if (!aditionalContentId.equals(other.aditionalContentId))
			return false;
		if (dateTimeReceived == null) {
			if (other.dateTimeReceived != null)
				return false;
		} else if (!dateTimeReceived.equals(other.dateTimeReceived))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (receiver == null) {
			if (other.receiver != null)
				return false;
		} else if (!receiver.equals(other.receiver))
			return false;
		if (sender == null) {
			if (other.sender != null)
				return false;
		} else if (!sender.equals(other.sender))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}
}
