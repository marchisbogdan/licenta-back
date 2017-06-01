package com.onnisoft.wahoo.api.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ApiDateTimeUtils {
	/**
	 * The function calculates the interval for the specified parameters.
	 * 
	 * By giving only the year as parameter, the function will return two dates
	 * which have an interval of one year, if you provide the year and month,
	 * the function will return two dates which have an interval of one month,
	 * and so on.
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @return an ArrayList with the start_date on position 0 and the end_date
	 *         on position 1
	 */
	public static List<Date> getDateIntervalBy(int year, int month, int day, int hour) {
		ArrayList<Date> dates = new ArrayList<>();
		boolean dateChanged = false;

		Date date_start = null;
		Date date_end = null;

		Calendar calendar_start = Calendar.getInstance();
		Calendar calendar_end = Calendar.getInstance();
		calendar_start.clear();
		calendar_end.clear();

		if (year > -1 && month > -1 && day > -1 && hour > -1) {
			dateChanged = true;
			calendar_start.set(year, month, day, hour, 0);
			calendar_end.set(year, month, day, hour, 0);
			calendar_end.add(Calendar.HOUR_OF_DAY, 1);
		} else if (year > -1 && month > -1 && day > -1) {
			dateChanged = true;
			calendar_start.set(year, month, day, 0, 0);
			calendar_end.set(year, month, day, 0, 0);
			calendar_end.add(Calendar.DAY_OF_MONTH, 1);
		} else if (year > -1 && month > -1) {
			dateChanged = true;
			calendar_start.set(year, month, 1, 0, 0);
			calendar_end.set(year, month, 1, 0, 0);
			calendar_end.add(Calendar.MONTH, 1);
		} else if (year > -1) {
			dateChanged = true;
			calendar_start.set(year, 0, 1, 0, 0);
			calendar_end.set(year, 0, 1, 0, 0);
			calendar_end.add(Calendar.YEAR, 1);
		}
		if (dateChanged) {
			date_start = calendar_start.getTime();
			date_end = calendar_end.getTime();
		}

		dates.add(0, date_start);
		dates.add(1, date_end);

		return dates;
	}
}
