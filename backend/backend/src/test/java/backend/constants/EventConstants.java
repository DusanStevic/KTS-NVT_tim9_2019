package backend.constants;

import java.util.Date;

import backend.dto.CreateEventDTO;
import backend.model.Event;
import backend.model.EventType;

public class EventConstants {

	public static final int DB_EVENT_COUNT = 7; //koliko ih ima u bazi
	
	public static final Long DB_EVENT_ID = 3L;
	public static final Long DB_EVENT_DELETED = 4L;
	public static final Long DB_EVENT_TO_BE_UPDATED = 5L;
	public static final Long DB_EVENT_TO_BE_DELETED = 6L;
	public static final Long DB_EVENT_TO_BE_DELETED2 = 7L;
	public static final Long EVENT_ID_NON_EXISTENT = 666L;
	
	public static final Long NEW_EVENT_LOCATION_ID = 3L;
	
	public static final String DB_EVENT_NAME = "Event";
	public static final Long DB_EVENT_LOCATION_ID = 1L;
	
	public static final String DB_EVENT_DELETED_NAME = "Event deleted";
	
	public static Event NEW_EVENT = new Event("Novi Event", "novi",EventType.CONCERT,22,null);
	public static Event UPD_EVENT = new Event("UPD Event", "apdejtovani",EventType.CULTURE,55);
	public static CreateEventDTO NEW_EVENT_DTO = new CreateEventDTO("NoviEventDTO","opis",1,new Date(),new Date(),250,5,1L,null);
}
