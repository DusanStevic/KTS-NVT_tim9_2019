package backend.constants;

public class ChartConstants {
	
	//System Informations
	public static final int INFO_NUM_EVENTS = 2;
	public static final int INFO_NUM_ADMIN = 1;
	public static final int INFO_NUM_USERS = 2;
	public static final double INFO_ALLTIME_INCOME = 2300.0;
	public static final int INFO_ALLTIME_TICKETS = 5;
	
	//Incomes and no. of tickets sold by events
	public static final String EVENT1_NAME = "UNIPARTY";
	public static final double INCOME_EVENT1 = 1400.0;
	public static final double TICKETS_SOLD_EVENT1 = 2.0;
	
	public static final String EVENT2_NAME = "FolkFest";
	public static final double INCOME_EVENT2 = 900.0;
	public static final double TICKETS_SOLD_EVENT2 = 3.0;
	
	public static final String AVERAGE_NAME = "Average";
	public static final double INCOME_EVENT_AVERAGE = 1150.0;
	public static final double TICKETS_SOLD_AVERAGE = 2.5;
	
	//Incomes and no. of tickets sold by locations

	public static final String LOCATION1_NAME = "SPENS NS";
	public static final double INCOME_LOCATION1 = 2300.0;
	public static final double TICKETS_SOLD_LOCATION1 = 5.0;
	public static final double INCOME_LOCATION1_INTERVAL = 1400.0;
	public static final double TICKETS_SOLD_LOCATION1_INTERVAL = 2.0;
	
	//Intervals - Good, good but empty, bad
	public static final String START_DATE_GOOD = "2020-01-01";
	public static final String END_DATE_GOOD = "2020-04-01";
	public static final String START_DATE_EMPTY = "2020-01-01";
	public static final String END_DATE_EMPTY = "2020-02-01";
	public static final String START_DATE_BAD = "2019-08-11";
	public static final String END_DATE_BAD = "2019-04-01";
	
}
