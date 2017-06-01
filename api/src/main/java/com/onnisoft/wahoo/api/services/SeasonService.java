package com.onnisoft.wahoo.api.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.document.Event;
import com.onnisoft.wahoo.model.document.EventCompetitor;
import com.onnisoft.wahoo.model.document.Round;
import com.onnisoft.wahoo.model.document.Season;

@Component
public class SeasonService extends AbstractService<Season> {

	@Autowired
	private Dao<Season> seasonDao;

	@Autowired
	private Dao<Round> roundDao;

	@Autowired
	private Dao<Event> eventDao;

	@Autowired
	private Dao<EventCompetitor> eventCompetitorDao;

	@Override
	void inactivateById(String id) {
		// TODO Auto-generated method stub

	}

	public Season updateSeasonStats(Season season) {
		// calculate the number of rounds and the number of teams in a season
		logger.info("Calculating stats for season with the id:" + season.getId() + " ......");
		List<Round> rounds = this.roundDao.retrieveList(new Round.Builder().season(season).build());
		List<Event> events = rounds.stream().map(round -> this.eventDao.retrieveList(new Event.Builder().round(round).build())).flatMap(List::stream)
				.collect(Collectors.toList());
		int numberOfDistinctCompetitors = events.stream().map(event -> this.eventCompetitorDao.retrieveList(new EventCompetitor.Builder().event(event).build()))
				.flatMap(List::stream).map(ec -> ec.getCompetitor().getId()).distinct().collect(Collectors.toList()).size();

		boolean updated = seasonDao.update(new Season.Builder().id(season.getId()).rounds(rounds.size()).numTeam(numberOfDistinctCompetitors).builder());

		if (!updated) {
			logger.warn("Problems when updating season: id:" + season.getId());
		} else {
			logger.info("Finished calculating stats for season with id:" + season.getId());
		}

		return this.seasonDao.retrieveById(season.getId());
	}
}
