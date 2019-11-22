package backend.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.dto.charts.ChartEventTicketsSoldDTO;
import backend.dto.charts.ChartIncomeEventsDTO;
import backend.dto.charts.DateIntervalDTO;
import backend.dto.charts.SystemInformationsDTO;
import backend.service.ChartService;
import backend.service.EventService;

@RestController
@RequestMapping("/api/charts")
public class ChartController {

	@Autowired
	ChartService chartService;

	@Autowired
	EventService eventService;

	@PreAuthorize("hasAnyRole('ROLE_SYS_ADMIN')")
	@GetMapping(path = "/sysinfo", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SystemInformationsDTO> getSysInfo() {
		try {
			SystemInformationsDTO info = chartService.systemInformations();
			if (info != null) {
				return ResponseEntity.ok().body(info);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.build();
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_SYS_ADMIN')")
	@GetMapping(path = "/event_incomes", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ChartIncomeEventsDTO>> getIncomeByEvents() {
		try {
			List<ChartIncomeEventsDTO> info = chartService.incomeByEvents();
			if (info != null) {
				return ResponseEntity.ok().body(info);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.build();
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_SYS_ADMIN')")
	@GetMapping(path = "/event_tickets_sold", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ChartEventTicketsSoldDTO>> getTicketsSoldByEvents() {
		try {
			List<ChartEventTicketsSoldDTO> info = chartService
					.soldTicketsByEvents();
			if (info != null) {
				return ResponseEntity.ok().body(info);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.build();
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_SYS_ADMIN')")
	@PutMapping(path = "/event_incomes/interval", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ChartIncomeEventsDTO>> getIncomeByEvents(@Valid @RequestBody DateIntervalDTO interval) {
		try {
			List<ChartIncomeEventsDTO> info = chartService.incomeByEvents(interval);
			if (info != null) {
				return ResponseEntity.ok().body(info);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.build();
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_SYS_ADMIN')")
	@PutMapping(path = "/event_tickets_sold/interval", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ChartEventTicketsSoldDTO>> getTicketsSoldByEvents(@Valid @RequestBody DateIntervalDTO interval) {
		try {
			List<ChartEventTicketsSoldDTO> info = chartService
					.soldTicketsByEvents(interval);
			if (info != null) {
				return ResponseEntity.ok().body(info);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.build();
		}
	}

}
