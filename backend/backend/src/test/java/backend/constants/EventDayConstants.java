package backend.constants;

import java.util.Date;

import backend.model.EventDay;
import backend.model.EventStatus;

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
	
	public static EventDay NEW_EVENTDAY = new EventDay(null, "Novi EventDay", "novi",new Date(),EventStatus.ACTIVE,null,null,false);
	public static EventDay UPD_EVENTDAY = new EventDay("UPD EventDay","apdejtovani");
}
