package com.lab.service.command;

import java.util.concurrent.CompletableFuture;

import com.lab.dto.AppointmentDTO;

public interface AppointmentCommandService {

	CompletableFuture<String> createAppointment(AppointmentDTO appointmentDTO);

}