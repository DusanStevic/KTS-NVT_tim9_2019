package backend.constants;

import backend.dto.HallDTO;
import backend.model.Hall;
import backend.model.Location;

public class HallConstants {

	public static final int DB_HALL_COUNT = 5;
	
	public static final Long DB_HALL_ID = 1L;
	public static final Long DB_HALL_DELETED = 2L;
	public static final Long DB_HALL_TO_BE_UPDATED = 3L;
	public static final Long DB_HALL_TO_BE_DELETED = 4L;
	public static final Long DB_HALL_TO_BE_DELETED2 = 5L;
	public static final Long HALL_ID_NON_EXISTENT = 666L;
	
	public static final Long NEW_HALL_LOCATION_ID = 1L;
	
	public static final String DB_HALL_NAME = "Main Hall";
	public static final Long DB_HALL_LOCATION_ID = 1L;
	
	public static final String DB_HALL_DELETED_NAME = "Main Hall deleted";
	
	public static Hall NEW_HALL = new Hall("Nova Dvodarna", null);
	public static Hall UPD_HALL = new Hall("UPD Dvodarna", null);
	public static HallDTO NEW_HALL_DTO = new HallDTO("Novo");
}
