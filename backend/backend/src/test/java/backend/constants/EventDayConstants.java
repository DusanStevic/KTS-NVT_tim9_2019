package backend.constants;


public class EventDayConstants {
	public static final int DB_EVENTDAY_COUNT = 7; //koliko ih ima u bazi
	
	public static final Long DB_EVENTDAY_ID = 3L;
	public static final Long DB_EVENTDAY_DELETED = 4L;
	public static final Long DB_EVENTDAY_TO_BE_UPDATED = 5L;
	public static final Long DB_EVENTDAY_TO_BE_DELETED = 6L;
	public static final Long DB_EVENTDAY_TO_BE_DELETED2 = 7L;
	public static final Long EVENTDAY_ID_NON_EXISTENT = 666L;
	
	public static final String DB_EVENTDAY_NAME = "EventDay";
	
	public static final String DB_EVENTDAY_DELETED_NAME = "EventDay deleted";
	
	//public static Event NEW_EVENTDAY = new Event("Novi EventDay", "novi",EventType.CONCERT,22,null);
	//public static Event UPD_EVENTDAY = new Event("UPD EventDay", "apdejtovani",EventType.CULTURE,55);
}
