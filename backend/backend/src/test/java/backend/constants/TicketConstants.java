package backend.constants;

import org.springframework.data.domain.PageRequest;

public class TicketConstants {
	public static final Integer DB_COUNT = 5;
	public static final int PAGE_SIZE = 5;
	public static final PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE);
	
	public static final Long NEW_TICKET_EVENDAY_ID = 1L;
	public static final Long NEW_TICKET_EVENTSECTOR_ID = 2L;
	public static final Long NEW_TICKET_RESERVATION_ID = 1L;
	public static final boolean NEW_HAS_SEAT = true;
	public static final Integer NEW_NUM_COL = 2;
	public static final Integer NEW_NUM_ROW = 2;
	
	
	public static final Long TICKET1_ID = 1L;	//ticket for event 1
	public static final	boolean TICKET1_HAS_SEAT = true;
	public static final Integer TICKET1_COL = 1;
	public static final Integer TICKET1_ROW = 1;
	public static final Long TICKET1_EV_SECTOR_ID = 2L;
	public static final Long TICKET1_EVENTDAY_ID = 1L;
	public static final Long TICKET1_RESERVATION_ID = 1L;
	
	public static final Long TICKET3_ID = 3L;	//ticket for event 2
	public static final	boolean TICKET3_HAS_SEAT = true;
	public static final Integer TICKET3_COL = 1;
	public static final Integer TICKET3_ROW = 1;
	public static final Long TICKET3_EV_SECTOR_ID = 4L;

	public static final Long TICKET_ID_NONEXISTENT = 666L;
	
	public static final Long EVENT2_ID = 2L;
	public static final Long EVENT_ID_NONEXISTENT = 666L;
	public static final Integer EVENT2_TICKET_COUNT = 3;
	
	public static final Long LOCATION1_ID = 1L;
	public static final Long LOCATION_ID_EMPTY = 3L;
	public static final Long LOCATION_ID_NON_EXISTENT = 7L;
	
	public static final String LOCATION_DATE_GOOD = "2020-02-01";
	public static final String LOCATION_DATE_EMPTY = "2021-02-01";
	
	public static final Long RESERVATION_ID = 1L;
	public static final Integer RESERVATION_TICKET_COUNT = 1;
	public static final Long RESERVATION_ID_NON_EXISTENT = 777L;
	
}
