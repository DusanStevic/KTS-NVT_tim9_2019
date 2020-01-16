package backend.constants;

import backend.dto.SittingSectorDTO;
import backend.dto.StandingSectorDTO;
import backend.model.SittingSector;
import backend.model.StandingSector;

public class SectorConstants {

public static final int DB_SECTOR_COUNT = 10;
	
	public static final Long DB_STAND_SECTOR_ID = 1L;
	public static final Long DB_STAND_SECTOR_DELETED = 2L;
	public static final Long DB_STAND_SECTOR_TO_BE_UPDATED = 3L;
	
	public static final Long DB_SIT_SECTOR_ID = 4L;
	public static final Long DB_SIT_SECTOR_DELETED = 5L;
	public static final Long DB_SIT_SECTOR_TO_BE_UPDATED = 6L;
	
	public static final Long DB_STAND_SECTOR_TO_BE_DELETED = 7L;
	public static final Long DB_STAND_SECTOR_TO_BE_DELETED2 = 8L;
	public static final Long DB_SIT_SECTOR_TO_BE_DELETED = 9L;
	public static final Long DB_SIT_SECTOR_TO_BE_DELETED2 = 10L;
	
	public static final Long SECTOR_ID_NON_EXISTENT = 666L;
	
	public static final Long NEW_SECTOR_HALL_ID = 1L;
	
	public static final String DB_STAND_SECTOR_NAME = "Stand";
	public static final Long DB_SECTOR_HALL_ID = 1L;
	public static final int DB_STAND_SECTOR_CAPACITY = 500;
	
	public static final String DB_SIT_SECTOR_NAME = "Sit";
	public static final int DB_SIT_SECTOR_ROWS = 9;
	public static final int DB_SIT_SECTOR_COLS = 10;
	
	public static final String DB_STAND_SECTOR_DELETED_NAME = "Stand deleted";
	public static final String DB_SIT_SECTOR_DELETED_NAME = "Sit deleted";
	
	public static StandingSector NEW_STAND_SECTOR = new StandingSector("Novi standing sector", 100);
	public static SittingSector NEW_SIT_SECTOR = new SittingSector("Novi sitting sector", 6, 8);
	
	public static StandingSector UPD_STAND = new StandingSector("Upd stand", 2000);
	public static SittingSector UPD_SIT = new SittingSector("Upd sit", 20, 10);
	
	public static final SittingSectorDTO NEW_SIT_DTO = new SittingSectorDTO("sit123", 9, 12);
	public static final StandingSectorDTO NEW_STAND_DTO = new StandingSectorDTO("stand123", 1000);
	
	public static final SittingSectorDTO UPD_SIT_DTO = new SittingSectorDTO("novo ime", 3, 11);
	public static final StandingSectorDTO UPD_STAND_DTO = new StandingSectorDTO("novo ime", 963);
}
