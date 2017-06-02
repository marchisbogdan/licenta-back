package com.onnisoft.wahoo.model.document;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="products")
public class Product extends Node {

	private static final long serialVersionUID = -5864086433246035463L;
	
	private String name;
	private String description;
	private String imageURL;
	private Date bidEndDate;
	@DBRef
	private List<Comment> comments;
	private long startingPrice;
	private long highestPrice;
	private int quantity;
	
	public Product() {
	}

	private Product(Builder builder) {
		super(builder.id, builder.creationDate, builder.updateDate);
		this.name = builder.name;
		this.description = builder.description;
		this.imageURL = builder.imageURL;
		this.bidEndDate = builder.bidEndDate;
		this.comments = builder.comments;
		this.startingPrice = builder.startingPrice;
		this.highestPrice = builder.highestPrice;
		this.quantity = builder.quantity;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getImageURL() {
		return imageURL;
	}

	public Date getBidEndDate() {
		return bidEndDate;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public long getStartingPrice() {
		return startingPrice;
	}

	public long getHighestPrice() {
		return highestPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((bidEndDate == null) ? 0 : bidEndDate.hashCode());
		result = prime * result + ((comments == null) ? 0 : comments.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + (int) (highestPrice ^ (highestPrice >>> 32));
		result = prime * result + ((imageURL == null) ? 0 : imageURL.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + quantity;
		result = prime * result + (int) (startingPrice ^ (startingPrice >>> 32));
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
		Product other = (Product) obj;
		if (bidEndDate == null) {
			if (other.bidEndDate != null)
				return false;
		} else if (!bidEndDate.equals(other.bidEndDate))
			return false;
		if (comments == null) {
			if (other.comments != null)
				return false;
		} else if (!comments.equals(other.comments))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (highestPrice != other.highestPrice)
			return false;
		if (imageURL == null) {
			if (other.imageURL != null)
				return false;
		} else if (!imageURL.equals(other.imageURL))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (quantity != other.quantity)
			return false;
		if (startingPrice != other.startingPrice)
			return false;
		return true;
	}
	
	public static final class Builder {
		private String id;
		private String name;
		private String description;
		private String imageURL;
		private Date bidEndDate;
		@DBRef
		private List<Comment> comments;
		private long startingPrice;
		private long highestPrice;
		private int quantity;
		private Date creationDate;
		private Date updateDate;
		
		private boolean isCreated;
		
		public Builder id(String id){
			this.id= id;
			return this;
		}
		public Builder name(String name){
			this.name = name;
			return this;
		}
		public Builder description(String description){
			this.description = description;
			return this;
		}
		public Builder imageURL(String imageURL){
			this.imageURL = imageURL;
			return this;
		}
		public Builder bidEndDate(Date bidEndDate){
			this.bidEndDate = bidEndDate;
			return this;
		}
		public Builder comments(List<Comment> comments){
			this.comments = comments;
			return this;
		}
		public Builder startingPrice(long startingPrice){
			this.startingPrice = startingPrice;
			return this;
		}
		public Builder highestPrice(long highestPrice){
			this.highestPrice = highestPrice;
			return this;
		}
		public Builder quantity(int quantity){
			this.quantity = quantity;
			return this;
		}
		public Builder toCreate(){
			this.isCreated = true;
			return this;
		}
		public Product build(){
			if(isCreated){
				this.creationDate = new Date();
			}else{
				this.updateDate = new Date();
			}
			return new Product(this);
		}
	}
}
