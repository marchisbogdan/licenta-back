package com.onnisoft.wahoo.api.request;

import java.io.Serializable;
import java.util.Date;

public class ProductDTO implements Serializable {

	private static final long serialVersionUID = 485832690426365384L;
	
	private String name;
	private String description;
	private String imageURL;
	private Date bidEndDate;
	private long startingPrice;
	private long highestPrice;
	private int quantity;
	
	public ProductDTO() {
	}

	public ProductDTO(String name, String description, String imageURL, Date bidEndDate, long startingPrice,
			long highestPrice, int quantity) {
		super();
		this.name = name;
		this.description = description;
		this.imageURL = imageURL;
		this.bidEndDate = bidEndDate;
		this.startingPrice = startingPrice;
		this.highestPrice = highestPrice;
		this.quantity = quantity;
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
		int result = 1;
		result = prime * result + ((bidEndDate == null) ? 0 : bidEndDate.hashCode());
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
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductDTO other = (ProductDTO) obj;
		if (bidEndDate == null) {
			if (other.bidEndDate != null)
				return false;
		} else if (!bidEndDate.equals(other.bidEndDate))
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
}
