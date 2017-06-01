package com.onnisoft.wahoo.api.services;

import java.util.List;
import java.util.concurrent.Future;

import com.onnisoft.api.utils.security.dto.GenericResponseDTO;
import com.onnisoft.wahoo.model.document.Country;
import com.onnisoft.wahoo.model.document.Event;
import com.onnisoft.wahoo.model.document.EventCompetitor;
import com.onnisoft.wahoo.model.document.Player;
import com.onnisoft.wahoo.model.document.PlayerEventStatistics;
import com.onnisoft.wahoo.model.document.PlayerEventTimestampStatistic;
import com.onnisoft.wahoo.model.document.RealCompetition;
import com.onnisoft.wahoo.model.document.RealCompetitor;
import com.onnisoft.wahoo.model.document.Round;
import com.onnisoft.wahoo.model.document.Season;
import com.onnisoft.wahoo.model.document.Sport;

/**
 * 
 * Defines methods for importing data from Mako.
 *
 * @author mbozesan
 * @date 31 Oct 2016 - 11:26:24
 *
 */
public interface ImportService {

	GenericResponseDTO<List<Country>> getCountryList();

	GenericResponseDTO<List<Player>> getPlayerList();

	GenericResponseDTO<List<Player>> getPlayerListByCompetitor(String competitorId);

	GenericResponseDTO<List<Player>> getPlayerListByRange(int range, int max_results);

	Future<GenericResponseDTO<List<RealCompetitor>>> getRealCompetitorList();

	GenericResponseDTO<List<Sport>> getSportList();

	Future<GenericResponseDTO<List<RealCompetition>>> getRealCompetitionList(String sportId);

	Future<GenericResponseDTO<List<Season>>> getSeasonList(String competitionId);

	Future<GenericResponseDTO<List<Round>>> getRoundList(String seasonId);

	Future<GenericResponseDTO<List<Event>>> getEventList(String roundId);

	Future<GenericResponseDTO<List<EventCompetitor>>> getEventCompetitorListByEventId(String eventId);

	Future<GenericResponseDTO<List<EventCompetitor>>> getEventCompetitorListByCompetitorId(String competitorId);

	Future<GenericResponseDTO<List<PlayerEventStatistics>>> getPlayerEventStatisticsListByPlayerId(String playerId);

	Future<GenericResponseDTO<List<PlayerEventTimestampStatistic>>> getPlayerEventTimestampStatisticByPlayerId(String playerId);
}