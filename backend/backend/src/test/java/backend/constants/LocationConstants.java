package backend.constants;

import java.sql.Timestamp;

import backend.dto.LocationDTO;
import backend.model.Location;

public class LocationConstants {

	public static final Timestamp FIRST_TIMESTAMP = new Timestamp(0L);
	public static final int PAGE_SIZE = 5;
	
	public static final Long DB_LOCATION_ID = 1L;
	public static final Long DB_LOCATION_ID_DELETED = 2L;
	public static final Long DB_LOCATION_ID_TO_BE_UPDATED = 3L;
	public static final Long DB_LOCATION_ID_TO_BE_DELETED = 4L;
	public static final Long LOCATION_ID_NON_EXISTENT = 666L;
	
	public static final String DB_LOCATION_NAME = "SPENS NS";
	public static final String DB_DELETED_LOCATION_NAME = "SPENS NS deleted";
	public static final Long DB_LOCATION_ADDRESS_ID = 3L;
	public static final Long DB_DELETED_LOCATION_ADDRESS_ID = 4L;
	
	public static final int DB_LOCATION_COUNT = 5;
	
	
	public static Location NEW_LOCATION = new Location("NOVA", "Nova lokacija je u gradu", FIRST_TIMESTAMP, "Mihaila Pupina 34", 33.3, 22.2);
	public static final Long NEW_LOCATION_ADDRESS_ID = 7L;
	public static final Long UPD_LOCATION_ADDRESS_ID = 5L;
	
	public static Location UPD_LOCATION = new Location("UPDATED", "Azurirana lokacija je u gradu", FIRST_TIMESTAMP, "adresa azurirana", 44.2, 66.03);
	public static LocationDTO DTO_NEW_LOCATION = new LocationDTO("NOVA", "Nova lokacija je u gradu", "adresa lokacija dto", 22.3, 33.2);
}
