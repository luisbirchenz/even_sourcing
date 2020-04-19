package com.lab.service.query;

import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppoinmentQueryServiceImpl implements AppointmentQueryService {
	
    private final EventStore eventStore;

    public AppoinmentQueryServiceImpl(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    public List<Object> listOfEventsForAppointment(String appointmentId) {
        return eventStore.readEvents(appointmentId)
        		.asStream().map( 
        				s -> s.getPayload())
        		.collect(Collectors.toList());
    }

}
