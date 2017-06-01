package com.onnisoft.wahoo.model.document.enums;

import java.util.ArrayList;
import java.util.List;

public enum PlayerPositionEnum {
	GOALKEEPER, SWEEPER, CENTRE_BACK, FULL_BACK, WING_BACK, DEFENSIVE_MIDFIELD, CENTRE_MIDFIELD, WIDE_MIDFIELD, ATTACKING_MIDFIELD, SECOND_STRIKER, WINGER, CENTRE_FORWARD;

	public static List<String> defensePositions() {
		List<String> defensePositions = new ArrayList<>();
		defensePositions.add(SWEEPER.name());
		defensePositions.add(CENTRE_BACK.name());
		defensePositions.add(FULL_BACK.name());
		defensePositions.add("LEFT_" + WING_BACK.name());
		defensePositions.add("RIGHT_" + WING_BACK.name());
		return defensePositions;
	}

	public static List<String> midfieldPositions() {
		List<String> midfieldPositions = new ArrayList<>();
		midfieldPositions.add(DEFENSIVE_MIDFIELD.name());
		midfieldPositions.add(CENTRE_MIDFIELD.name());
		midfieldPositions.add("LEFT_" + WIDE_MIDFIELD.name());
		midfieldPositions.add("RIGHT_" + WIDE_MIDFIELD.name());
		midfieldPositions.add(ATTACKING_MIDFIELD.name());
		return midfieldPositions;
	}

	public static List<String> attackPositions() {
		List<String> attackPositions = new ArrayList<>();
		attackPositions.add(SECOND_STRIKER.name());
		attackPositions.add("LEFT_" + WINGER.name());
		attackPositions.add("RIGHT_" + WINGER.name());
		attackPositions.add(CENTRE_FORWARD.name());
		return attackPositions;
	}
}
