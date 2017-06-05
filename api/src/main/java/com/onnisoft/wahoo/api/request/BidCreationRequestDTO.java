package com.onnisoft.wahoo.api.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class BidCreationRequestDTO implements Serializable{

	private static final long serialVersionUID = 3868736723795079420L;

	@NotNull
	private String idProduct;
	@NotNull
	private long bidValue;
	
	public BidCreationRequestDTO() {
	}

	public BidCreationRequestDTO(String idProduct, long bidValue) {
		super();
		this.idProduct = idProduct;
		this.bidValue = bidValue;
	}

	public String getIdProduct() {
		return idProduct;
	}

	public long getBidValue() {
		return bidValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (bidValue ^ (bidValue >>> 32));
		result = prime * result + ((idProduct == null) ? 0 : idProduct.hashCode());
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
		BidCreationRequestDTO other = (BidCreationRequestDTO) obj;
		if (bidValue != other.bidValue)
			return false;
		if (idProduct == null) {
			if (other.idProduct != null)
				return false;
		} else if (!idProduct.equals(other.idProduct))
			return false;
		return true;
	}
}
