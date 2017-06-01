package com.onnisoft.wahoo.model.document.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mbozesan on 10/10/16.
 */
public enum PlayerGamePositionEnum {
	GOALKEEPER, SWEEPER, CENTRE_BACK, FULL_BACK, LEFT_WING_BACK, RIGHT_WING_BACK, DEFENSIVE_MIDFIELD, CENTRE_MIDFIELD, LEFT_WIDE_MIDFIELD, RIGHT_WIDE_MIDFIELD, ATTACKING_MIDFIELD, SECOND_STRIKER, LEFT_WINGER, RIGHT_WINGER, CENTRE_FORWARD;

	public static List<String> defensePositions() {
		List<String> defensePositions = new ArrayList<>();
		defensePositions.add(SWEEPER.name());
		defensePositions.add(CENTRE_BACK.name());
		defensePositions.add(FULL_BACK.name());
		defensePositions.add(LEFT_WING_BACK.name());
		defensePositions.add(RIGHT_WING_BACK.name());
		return defensePositions;
	}

	public static List<String> midfieldPositions() {
		List<String> midfieldPositions = new ArrayList<>();
		midfieldPositions.add(DEFENSIVE_MIDFIELD.name());
		midfieldPositions.add(CENTRE_MIDFIELD.name());
		midfieldPositions.add(LEFT_WIDE_MIDFIELD.name());
		midfieldPositions.add(RIGHT_WIDE_MIDFIELD.name());
		midfieldPositions.add(ATTACKING_MIDFIELD.name());
		return midfieldPositions;
	}

	public static List<String> attackPositions() {
		List<String> attackPositions = new ArrayList<>();
		attackPositions.add(SECOND_STRIKER.name());
		attackPositions.add(LEFT_WINGER.name());
		attackPositions.add(RIGHT_WINGER.name());
		attackPositions.add(CENTRE_FORWARD.name());
		return attackPositions;
	}
}
