package backend.constants;

import java.util.Date;

import backend.model.Reservation;

public class ReservationConstants {

	public static final int DB_RESERVATION_COUNT = 9; //koliko ih ima u bazi
	
	public static final Long DB_RESERVATION_ID = 5L;
	public static final Long DB_RESERVATION_DELETED = 6L;
	public static final Long DB_RESERVATION_TO_BE_UPDATED = 7L;
	public static final Long DB_RESERVATION_TO_BE_DELETED = 8L;
	public static final Long DB_RESERVATION_TO_BE_DELETED2 = 9L;
	public static final Long RESERVATION_ID_NON_EXISTENT = 666L;
	
	public static final String DB_RESERVATION_NAME = "Reservation";
	
	public static final String DB_RESERVATION_DELETED_NAME = "Reservation deleted";
	
	public static Reservation NEW_RESERVATION = new Reservation(null,false,new Date(),null ,null,false);
	public static Reservation UPD_RESERVATION = new Reservation(null,false,new Date(),null ,null,false);
}

