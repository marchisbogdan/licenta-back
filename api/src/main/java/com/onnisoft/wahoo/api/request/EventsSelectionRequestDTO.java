package com.onnisoft.wahoo.api.request;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * Events selection request. It contains a list of event ids and the id of the
 * virtual competition to which we assign these events. Also, it contains a
 * selectedAll boolean which if true, will result in neglecting the eventIds and
 * pulling all the events of the selected rounds from database.
 *
 * @author mbozesan
 * @date 8 Nov 2016 - 11:32:29
 *
 */
public class EventsSelectionRequestDTO implements Serializable {

	private static final long serialVersionUID = 5435336499089076725L;

	private String virtualCompetitionId;
	private List<String> eventIds;
	private boolean selectedAll;

	public EventsSelectionRequestDTO() {
	}

	public EventsSelectionRequestDTO(String virtualCompetitionId, List<String> eventIds, boolean selectedAll) {
		this.virtualCompetitionId = virtualCompetitionId;
		this.eventIds = eventIds;
		this.selectedAll = selectedAll;
	}

	/**
	 * @return the virtualCompetitionId
	 */
	public String getVirtualCompetitionId() {
		return virtualCompetitionId;
	}

	/**
	 * @return the eventIds
	 */
	public List<String> getEventIds() {
		return eventIds;
	}

	/**
	 * @return the all
	 */
	public boolean isSelectedAll() {
		return selectedAll;
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
		result = prime * result + (selectedAll ? 1231 : 1237);
		result = prime * result + ((eventIds == null) ? 0 : eventIds.hashCode());
		result = prime * result + ((virtualCompetitionId == null) ? 0 : virtualCompetitionId.hashCode());
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
		EventsSelectionRequestDTO other = (EventsSelectionRequestDTO) obj;
		if (selectedAll != other.selectedAll)
			return false;
		if (eventIds == null) {
			if (other.eventIds != null)
				return false;
		} else if (!eventIds.equals(other.eventIds))
			return false;
		if (virtualCompetitionId == null) {
			if (other.virtualCompetitionId != null)
				return false;
		} else if (!virtualCompetitionId.equals(other.virtualCompetitionId))
			return false;
		return true;
	}
}
