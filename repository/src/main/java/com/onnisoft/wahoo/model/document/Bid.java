package com.onnisoft.wahoo.model.document;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bids")
public class Bid implements Serializable {

	private static final long serialVersionUID = -4833645697287478866L;
	
	@Id
	private long id;
	private String idSubscriber;
	private String idProduct;
	private long bidValue;
	private Date date;
	
	public Bid() {
	}

	public Bid(long id, String idSubscriber, String idProduct, long bidValue, Date date) {
		super();
		this.id = id;
		this.idSubscriber = idSubscriber;
		this.idProduct = idProduct;
		this.bidValue = bidValue;
		this.date = date;
	}

	public long getId() {
		return id;
	}

	public String getIdSubscriber() {
		return idSubscriber;
	}

	public String getIdProduct() {
		return idProduct;
	}

	public long getBidValue() {
		return bidValue;
	}

	public Date getDate() {
		return date;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (bidValue ^ (bidValue >>> 32));
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((idProduct == null) ? 0 : idProduct.hashCode());
		result = prime * result + ((idSubscriber == null) ? 0 : idSubscriber.hashCode());
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
		Bid other = (Bid) obj;
		if (bidValue != other.bidValue)
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id != other.id)
			return false;
		if (idProduct == null) {
			if (other.idProduct != null)
				return false;
		} else if (!idProduct.equals(other.idProduct))
			return false;
		if (idSubscriber == null) {
			if (other.idSubscriber != null)
				return false;
		} else if (!idSubscriber.equals(other.idSubscriber))
			return false;
		return true;
	}
}
