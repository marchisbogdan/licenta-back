package com.onnisoft.wahoo.model.document;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="comments")
public class Comment extends Node {

	private static final long serialVersionUID = 6876682939749727983L;
	
	@DBRef
	private Subscriber subscriber;
	private String content;
	
	public Comment() {
	}

	private Comment(Builder builder) {
		super(builder.id, builder.creationDate, builder.updateDate);
		this.subscriber = builder.subscriber;
		this.content = builder.content;
	}

	public Subscriber getSubscriber() {
		return subscriber;
	}

	public String getContent() {
		return content;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((subscriber == null) ? 0 : subscriber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (subscriber == null) {
			if (other.subscriber != null)
				return false;
		} else if (!subscriber.equals(other.subscriber))
			return false;
		return true;
	}
	
	public static final class Builder{
		private String id;
		private Subscriber subscriber;
		private String content;
		private Date creationDate;
		private Date updateDate;
		
		private boolean isCreated;
		
		public Builder id(String id){
			this.id = id;
			return this;
		}
		public Builder subscriber(Subscriber subscriber){
			this.subscriber = subscriber;
			return this;
		}
		public Builder content(String content){
			this.content = content;
			return this;
		}
		public Builder toCreate(){
			this.isCreated = true;
			return this;
		}
		public Comment  build(){
			if(isCreated){
				this.creationDate = new Date();
			}else{
				this.updateDate = new Date();
			}
			return new Comment(this);
		}
	}
}
