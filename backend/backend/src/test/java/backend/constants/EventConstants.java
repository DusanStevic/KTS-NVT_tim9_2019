package backend.constants;

import backend.dto.HallDTO;
import backend.model.Event;
import backend.model.EventType;
import backend.model.Hall;

public class EventConstants {

	public static final int DB_EVENT_COUNT = 7; //koliko ih ima u bazi
	
	public static final Long DB_EVENT_ID = 3L;
	public static final Long DB_EVENT_DELETED = 4L;
	public static final Long DB_EVENT_TO_BE_UPDATED = 5L;
	public static final Long DB_EVENT_TO_BE_DELETED = 6L;
	public static final Long DB_EVENT_TO_BE_DELETED2 = 7L;
	public static final Long EVENT_ID_NON_EXISTENT = 666L;
	
	public static final Long NEW_EVENT_LOCATION_ID = 1L;
	
	public static final String DB_EVENT_NAME = "Event";
	public static final Long DB_EVENT_LOCATION_ID = 1L;
	
	public static final String DB_EVENT_DELETED_NAME = "EventDay deleted";
	
	public static Event NEW_EVENT = new Event("Novi EventDay", "novi",EventType.CONCERT,22,null);
	public static Event UPD_EVENT = new Event("UPD EventDay", "apdejtovani",EventType.CULTURE,55);
	//public static EventDTO NEW_EVENT_DTO = new EventDTO("Novo", null);
}
