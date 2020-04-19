package com.lab.service.query;

import java.util.List;

public interface AppointmentQueryService {
	public List<Object> listOfEventsForAppointment(String appointmentId);
}