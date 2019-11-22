package backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.dto.SystemInformationsDTO;
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
	@GetMapping(path= "/sysinfo", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SystemInformationsDTO> getSysInfo()
	{
		try {
			SystemInformationsDTO info = chartService.systemInformations();
			if(info != null)
			{
				return ResponseEntity.ok().body(info);
			}else
			{
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
