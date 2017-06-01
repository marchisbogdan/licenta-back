package com.onnisoft.wahoo.subscriber.api.request;

import java.io.Serializable;

/**
 * 
 * Add to favorite request.
 *
 * @author mbozesan
 * @date 20 Oct 2016 - 15:19:49
 *
 */
public class AddToFavoriteRequestDTO implements Serializable {

	private static final long serialVersionUID = 2168715254807427287L;

	private String favoritePlayerId;
	private String favoriteRealCompetitorId;
	private String favoriteRealCompetitionId;
	private String favoriteSportId;

	public AddToFavoriteRequestDTO() {
	}

	public AddToFavoriteRequestDTO(String favoritePlayerId, String favoriteClubId, String favoriteLeagueId, String favoriteSportId) {
		this.favoritePlayerId = favoritePlayerId;
		this.favoriteRealCompetitorId = favoriteClubId;
		this.favoriteRealCompetitionId = favoriteLeagueId;
		this.favoriteSportId = favoriteSportId;
	}

	/**
	 * @return the favoritePlayerId
	 */
	public String getFavoritePlayerId() {
		return favoritePlayerId;
	}

	/**
	 * @return the favoriteRealCompetitorId
	 */
	public String getFavoriteRealCompetitorId() {
		return favoriteRealCompetitorId;
	}

	/**
	 * @return the favoriteRealCompetitionId
	 */
	public String getFavoriteRealCompetitionId() {
		return favoriteRealCompetitionId;
	}

	/**
	 * @return the favoriteSportId
	 */
	public String getFavoriteSportId() {
		return favoriteSportId;
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
		result = prime * result + ((favoriteRealCompetitorId == null) ? 0 : favoriteRealCompetitorId.hashCode());
		result = prime * result + ((favoriteRealCompetitionId == null) ? 0 : favoriteRealCompetitionId.hashCode());
		result = prime * result + ((favoritePlayerId == null) ? 0 : favoritePlayerId.hashCode());
		result = prime * result + ((favoriteSportId == null) ? 0 : favoriteSportId.hashCode());
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
		AddToFavoriteRequestDTO other = (AddToFavoriteRequestDTO) obj;
		if (favoriteRealCompetitorId == null) {
			if (other.favoriteRealCompetitorId != null)
				return false;
		} else if (!favoriteRealCompetitorId.equals(other.favoriteRealCompetitorId))
			return false;
		if (favoriteRealCompetitionId == null) {
			if (other.favoriteRealCompetitionId != null)
				return false;
		} else if (!favoriteRealCompetitionId.equals(other.favoriteRealCompetitionId))
			return false;
		if (favoritePlayerId == null) {
			if (other.favoritePlayerId != null)
				return false;
		} else if (!favoritePlayerId.equals(other.favoritePlayerId))
			return false;
		if (favoriteSportId == null) {
			if (other.favoriteSportId != null)
				return false;
		} else if (!favoriteSportId.equals(other.favoriteSportId))
			return false;
		return true;
	}
}
