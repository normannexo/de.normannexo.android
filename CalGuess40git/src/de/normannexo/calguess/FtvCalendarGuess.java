package de.normannexo.calguess;

import java.util.Calendar;

public class FtvCalendarGuess {
	private Calendar date;
	public static final int MONDAY = 1;
	public static final int TUESDAY = 2;
	public static final int WEDNESDAY = 3;
	public static final int THURSDAY = 4;
	public static final int FRIDAY = 5;
	public static final int SATURDAY = 6;
	public static final int SUNDAY = 7;

	
	private int iGuess;
	private String strGuess = null;
	private boolean right = false;
	private int iDayOfWeek;
	
	public FtvCalendarGuess(Calendar cal, int guess) {
		date = Calendar.getInstance();
		date = cal;
		iDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		iGuess = guess;
		
	}
	
	public FtvCalendarGuess(Calendar cal) {
		date = Calendar.getInstance();
		date = cal;
		iGuess = 0;
	}
	
	public Calendar getDate() {
		return date;
	}
	
	public void setGuess(String s) {
		strGuess = s;
	}
	public String getGuess() {
		return strGuess;
	}
	
	public boolean isCorrect() {
		
	
		if (iGuess == iDayOfWeek) {
			return true;
		} else {
			return false;
		}
	}
	
	
}
