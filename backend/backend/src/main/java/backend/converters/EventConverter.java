package backend.converters;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import backend.dto.EventDTO;
import backend.dto.EventDayDTO;
import backend.dto.EventSectorDTO;
import backend.dto.UserDTO;
import backend.model.Event;
import backend.model.EventDay;
import backend.model.EventSector;
import backend.model.EventType;
import backend.model.User;
import backend.service.LocationService;

@Component
public class EventConverter {

	@Autowired
	LocationService locationService;
	
	@Autowired
	EventDayConverter eventDayConverter;
	
	@Autowired
	EventSectorConverter eventSectorConverter;
	
	
	
	public Event EventDTO2Event(EventDTO dto) {
		Event e = new Event();
		e.setDescription(dto.getDescription());
		e.setName(dto.getName());
		//e.setVideoPath(dto.getVideo_path());
		
	
		e.setStartDate(dto.getStart_date());
		e.setEndDate(dto.getEnd_date());
		e.setNumDays(dto.getNum_days());
		e.setMaxTickets(dto.getMax_tickets());
		e.setEventType(EventType.values()[dto.getEvent_type()]);
		e.setLocation(locationService.findOne(dto.getLocation_id()));
		for(String img_path : dto.getImage_paths()) {
			e.getImagePaths().add(img_path);
		}
		for(String video_path : dto.getVideo_paths()) {
			e.getVideoPaths().add(video_path);
		}
		
		
		
		
	    int diff = (int) ((dto.getEnd_date().getTime() - dto.getStart_date().getTime()) / (1000 * 60 * 60 * 24));
		
		for(int i = 0; i <= diff; i++) {
			EventDayDTO ed_dto = dto.getEvent_days().get(i);
			EventDay ed = eventDayConverter.EventDayDTO2EventDay(ed_dto);
			ed.setEvent(e);
			ed.setDate(new Date(dto.getStart_date().getTime() + TimeUnit.DAYS.toMillis(i)));
			e.getEventDays().add(ed);
		}
		
		for(EventSectorDTO es_dto : dto.getSectors()) {
			EventSector es = eventSectorConverter.EventSectorDTO2EventSector(es_dto);
			es.setEvent(e);
			e.getEventSectors().add(es);
		}
		return e;
	}
	
	//konvertuje Event u EventDTO
	public static EventDTO Event2EventDTO(Event event) {
		return new EventDTO(event);
	}
}
