package backend.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.model.Event;

@Service
public class SchedulerService {

	@Autowired
	EventService eventService;
	
	//Logically delete all event
	public void logicalDeleteExpiredEvents() {
		try{
		List<Event> activeEvents = eventService.findAllActive();
		Date today = new Date();
		for (Event event : activeEvents) {
			if(event.getEndDate().before(today))
			{
				eventService.delete(event.getId());			
			}
		}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	
}
