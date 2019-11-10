package backend.converters;

import org.springframework.stereotype.Component;

import backend.dto.EventDayDTO;
import backend.model.EventDay;
import backend.model.EventStatus;

@Component
public class EventDayConverter {

	public EventDay EventDayDTO2EventDay(EventDayDTO dto) {
		EventDay ed = new EventDay();
		ed.setDescription(dto.getDescription());
		ed.setName(dto.getName());
		ed.setStatus(EventStatus.ACTIVE);
		return ed;
	}
}
