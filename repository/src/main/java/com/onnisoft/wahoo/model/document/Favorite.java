package com.onnisoft.wahoo.model.document;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
 * Entity that keeps information about the items marked as favourite.
 *
 * @author mbozesan
 * @date 23 Sep 2016 - 16:50:16
 *
 */
@Document(collection = "favorites")
public class Favorite implements Serializable {

	private static final long serialVersionUID = -3287367827659671365L;

	@Id
	private String id;
	private Subscriber subscriber;
	private Sport favoriteSport;
	private RealCompetition favoriteCompetition;
	private RealCompetitor favoriteCompetitor;
	private Player favoritePlayer;

	public Favorite() {
	}

	public Favorite(String id, Subscriber subscriber, Sport favoriteSport, RealCompetition favoriteCompetition, RealCompetitor favoriteCompetitor,
			Player favoritePlayer) {
		this.id = id;
		this.subscriber = subscriber;
		this.favoriteSport = favoriteSport;
		this.favoriteCompetition = favoriteCompetition;
		this.favoriteCompetitor = favoriteCompetitor;
		this.favoritePlayer = favoritePlayer;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the subscriber
	 */
	public Subscriber getSubscriber() {
		return subscriber;
	}

	/**
	 * @return the favoriteSport
	 */
	public Sport getFavoriteSport() {
		return favoriteSport;
	}

	/**
	 * @return the favoriteLeague
	 */
	public RealCompetition getFavoriteCompetition() {
		return favoriteCompetition;
	}

	/**
	 * @return the favoriteClub
	 */
	public RealCompetitor getFavoriteCompetitor() {
		return favoriteCompetitor;
	}

	/**
	 * @return the favoritePlayer
	 */
	public Player getFavoritePlayer() {
		return favoritePlayer;
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
		result = prime * result + ((favoriteCompetitor == null) ? 0 : favoriteCompetitor.hashCode());
		result = prime * result + ((favoriteCompetition == null) ? 0 : favoriteCompetition.hashCode());
		result = prime * result + ((favoritePlayer == null) ? 0 : favoritePlayer.hashCode());
		result = prime * result + ((favoriteSport == null) ? 0 : favoriteSport.hashCode());
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
		Favorite other = (Favorite) obj;
		if (favoriteCompetitor == null) {
			if (other.favoriteCompetitor != null)
				return false;
		} else if (!favoriteCompetitor.equals(other.favoriteCompetitor))
			return false;
		if (favoriteCompetition == null) {
			if (other.favoriteCompetition != null)
				return false;
		} else if (!favoriteCompetition.equals(other.favoriteCompetition))
			return false;
		if (favoritePlayer == null) {
			if (other.favoritePlayer != null)
				return false;
		} else if (!favoritePlayer.equals(other.favoritePlayer))
			return false;
		if (favoriteSport == null) {
			if (other.favoriteSport != null)
				return false;
		} else if (!favoriteSport.equals(other.favoriteSport))
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
