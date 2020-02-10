package backend.constants;

import backend.model.EventSector;
import static backend.constants.EventConstants.*;
import static backend.constants.SectorConstants.*;

public class EventSectorConstants {
	public static final int DB_EVENTSECTOR_COUNT = 9; //koliko ih ima u bazi
	
	public static final Long DB_EVENTSECTOR_ID = 5L;
	public static final Long DB_EVENTSECTOR_DELETED = 6L;
	public static final Long DB_EVENTSECTOR_TO_BE_UPDATED = 7L;
	public static final Long DB_EVENTSECTOR_TO_BE_DELETED = 8L;
	public static final Long DB_EVENTSECTOR_TO_BE_DELETED2 = 9L;
	public static final Long EVENTSECTOR_ID_NON_EXISTENT = 666L;
	
	public static final String DB_EVENT_NAME_FROM_SECTOR = "FolkFest";
	
	
	public static final double DB_EVENTSECTOR_PRICE = 300;
	
	public static final String DB_EVENT_DELETED_NAME_FROM_SECTOR = "FolkFest";
	
	public static EventSector NEW_EVENTSECTOR = new EventSector(22L, 200, NEW_EVENT,NEW_STAND_SECTOR,false);
	public static EventSector UPD_EVENTSECTOR = new EventSector(22L, 200, NEW_EVENT,NEW_STAND_SECTOR,false);
}
