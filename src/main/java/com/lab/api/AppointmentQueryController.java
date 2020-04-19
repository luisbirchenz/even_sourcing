package com.lab.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lab.service.query.AppointmentQueryService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/appointments")
@Api(value = "Appointment Queries", tags = "List of events for an appointment")
public class AppointmentQueryController {

	private final AppointmentQueryService appointmentQueryService;
	
	public AppointmentQueryController(AppointmentQueryService appointmentQueryService) {
		this.appointmentQueryService = appointmentQueryService;
	}
	
    @GetMapping("/{appointmentId}/events")
    public List<Object> listEventsForAccount(@PathVariable(value = "appointmentId") String appointmentId){
        return appointmentQueryService.listOfEventsForAppointment(appointmentId);
    }
}
