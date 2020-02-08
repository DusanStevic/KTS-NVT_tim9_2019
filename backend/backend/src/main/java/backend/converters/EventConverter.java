package backend.converters;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import backend.dto.CreateEventDTO;
import backend.dto.EventDTO;
import backend.dto.EventDayDTO;
import backend.dto.EventSectorDTO;
import backend.dto.EventUpdateDTO;
import backend.exceptions.ResourceNotFoundException;
import backend.model.Event;
import backend.model.EventDay;
import backend.model.EventSector;
import backend.model.EventStatus;
import backend.model.EventType;
import backend.service.LocationService;

@Component
public class EventConverter {

	@Autowired
	LocationService locationService;
	
	@Autowired
	EventDayConverter eventDayConverter;
	
	@Autowired
	EventSectorConverter eventSectorConverter;
	
	public Event CreateEventDTO2Event(CreateEventDTO dto) throws ResourceNotFoundException {
		Event e = new Event();
		e.getImagePaths().add("https://res.cloudinary.com/djxkexzcr/image/upload/v1574108286/lf4ddnka9rqe62creizz.jpg");
		e.setDescription(dto.getDescription());
		e.setName(dto.getName());
		e.setStartDate(dto.getStartDate());
		e.setEndDate(dto.getEndDate());
		e.setNumDays(dto.getNumDays());
		e.setMaxTickets(dto.getMaxTickets());
		e.setEventType(EventType.values()[dto.getEventType()]);
		e.setLocation(locationService.findOne(dto.getLocationId()));
		int diff = (int) ((dto.getEndDate().getTime() - dto.getStartDate().getTime()) 
				/ (1000 * 60 * 60 * 24));
		for(int i = 0; i <= diff; i++) {
			EventDay ed = new EventDay();
			ed.setName(e.getName() + " Day " + (i+1));
			ed.setDescription(e.getDescription() + " Day "+ (i+1));
			ed.setStatus(EventStatus.ACTIVE);
			ed.setEvent(e);
			ed.setDate(new Date(dto.getStartDate().getTime() + TimeUnit.DAYS.toMillis(i)));
			e.getEventDays().add(ed);
		}
		
		/*for(EventSectorDTO es_dto : dto.getSectors()) {
			EventSector es = eventSectorConverter.EventSectorDTO2EventSector(es_dto);
			es.setEvent(e);
			e.getEventSectors().add(es);
		}*/
		
		return e;
	}
	public Event EventUpdateDTO2Event(EventUpdateDTO dto) {
		Event e = new Event();
		e.setDescription(dto.getDescription());
		e.setName(dto.getName());
		//e.setMaxTickets(dto.getMax_tickets());
		//e.setEventType(EventType.values()[dto.getType()]);
		
		return e;
	}
	public Event EventDTO2Event(EventDTO dto) throws ResourceNotFoundException {
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
