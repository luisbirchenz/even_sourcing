package com.lab.service.command;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import com.lab.command.CreateAppointmentCommand;
import com.lab.dto.AppointmentDTO;

@Service
public class AppointmentCommandServiceImpl implements AppointmentCommandService {
	
	private final CommandGateway commandGateway;
	
	public AppointmentCommandServiceImpl(CommandGateway commandGateway) {
		this.commandGateway = commandGateway;
	}
	
	@Override
	public CompletableFuture<String> createAppointment(AppointmentDTO appointmentDTO) {
		return commandGateway.send(
				new CreateAppointmentCommand(
						UUID.randomUUID().toString(), 
						appointmentDTO.getStartDate(), 
						appointmentDTO.getEndDate(), 
						appointmentDTO.getProvider(),
						appointmentDTO.getUser())
				);
	}
}
