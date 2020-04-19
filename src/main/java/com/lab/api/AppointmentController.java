package com.lab.api;


import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import com.lab.dto.AppointmentDTO;
import com.lab.service.command.AppointmentCommandService;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/appointments")
@Api(value = "Appointment Commands", tags = "Appointment Commands Endpoints")
public class AppointmentController {
	
	private final AppointmentCommandService appointmentCommandService;
	
	public AppointmentController(AppointmentCommandService appointmentCommandService) {
		this.appointmentCommandService = appointmentCommandService;
	}
	
	@PostMapping
	public CompletableFuture<String> createAppointment(AppointmentDTO appointmentDTO) {
		return appointmentCommandService.createAppointment(appointmentDTO);
	}

}
