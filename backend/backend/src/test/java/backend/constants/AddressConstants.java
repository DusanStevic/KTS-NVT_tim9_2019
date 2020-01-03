package backend.constants;

import static backend.constants.AddressConstants.PAGE_SIZE;

import org.springframework.data.domain.PageRequest;

import backend.dto.AddressDTO;
import backend.model.Address;

public class AddressConstants {

	public static final int DB_ADDRESS_COUNT = 9;
	public static final int PAGE_SIZE = 5;
	public static final PageRequest pageRequest = PageRequest.of(1, PAGE_SIZE); //druga strana
	
	public static final Long DB_ADDRESS_ID = 3L;
	public static final String DB_ADDRESS_STREET = "Street3";
	public static final int DB_ADDRESS_STREET_NUM = 2;
	public static final String DB_ADDRESS_CITY = "Novi Sad";
	public static final String DB_ADDRESS_COUNTRY = "Serbia";
	public static final double DB_ADDRESS_LAT = 45.267136;
	public static final double DB_ADDRESS_LONG = 19.833549;
	public static final boolean DB_ADDRESS_DELETED = false;
	
	public static final Long ADDRESS_ID_NON_EXISTENT = 666L;
	
	public static final Long DB_ADDRESS_ID_DELETED = 1L;
	public static final String DB_ADDRESS_DELETED_STREET = "Street1";
	
	public static final Long DB_ADDRESS_ID_TO_BE_DELETED = 5L;
	
	public static final Long DB_ADDRESS_ID_TO_BE_UPDATED = 7L;
	public static final String UPD_ADDRESS_STREET = "Beogradska";
	public static final int UPD_ADDRESS_STREET_NUM = 999;
	public static final String UPD_ADDRESS_CITY = "Beograd";
	public static final String UPD_ADDRESS_COUNTRY = "Serbia";
	public static final double UPD_ADDRESS_LAT = -40.0003;
	public static final double UPD_ADDRESS_LONG = 80.0001;
	public static final Address UPD_ADDRESS = new Address(UPD_ADDRESS_STREET, UPD_ADDRESS_STREET_NUM, UPD_ADDRESS_CITY, UPD_ADDRESS_COUNTRY, UPD_ADDRESS_LAT, UPD_ADDRESS_LONG);
	
	public static final String NEW_ADDRESS_STREET = "Gogoljeva";
	public static final int NEW_ADDRESS_STREET_NUM = 22;
	public static final String NEW_ADDRESS_CITY = "Novi Sad";
	public static final String NEW_ADDRESS_COUNTRY = "Serbia";
	public static final double NEW_ADDRESS_LAT = 45.267136;
	public static final double NEW_ADDRESS_LONG = 19.833549;
	public static final boolean NEW_ADDRESS_DELETED = false;
	
	public static final Address NEW_ADDRESS = new Address(NEW_ADDRESS_STREET, NEW_ADDRESS_STREET_NUM, NEW_ADDRESS_CITY, NEW_ADDRESS_COUNTRY, NEW_ADDRESS_LAT, NEW_ADDRESS_LONG);
	public static final AddressDTO NEW_ADDRESS_DTO = new AddressDTO(NEW_ADDRESS_STREET, NEW_ADDRESS_STREET_NUM, NEW_ADDRESS_CITY, NEW_ADDRESS_COUNTRY, NEW_ADDRESS_LAT, NEW_ADDRESS_LONG);
}
