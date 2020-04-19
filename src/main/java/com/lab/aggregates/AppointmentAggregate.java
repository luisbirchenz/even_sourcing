package com.lab.aggregates;

import java.time.LocalDateTime;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import com.lab.command.CreateAppointmentCommand;
import com.lab.event.AppointmentAvailableEvent;
import com.lab.event.AppointmentCreatedEvent;

@Aggregate
public class AppointmentAggregate {
	
	@AggregateIdentifier
	private String id;
	private AppStatus status;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private String provider;
	private String user;
	
	public AppointmentAggregate() {}
	
	@CommandHandler
	public AppointmentAggregate(CreateAppointmentCommand cmd) {
		AggregateLifecycle.apply(new AppointmentCreatedEvent(cmd.id, cmd.startDate, cmd.endDate, cmd.provider, cmd.user));
	}
	
	@EventSourcingHandler
	private void on(AppointmentCreatedEvent appointmentCreatedEvent) {
		this.id = appointmentCreatedEvent.id;
		this.startDate = appointmentCreatedEvent.startDate;
		this.endDate = appointmentCreatedEvent.endDate;
		this.provider = appointmentCreatedEvent.provider;
		this.user = appointmentCreatedEvent.user;
		
		AggregateLifecycle.apply(new AppointmentAvailableEvent(this.id));
	}
	
	@EventSourcingHandler
    protected void on(AppointmentAvailableEvent appointmentAvailableEvent){
        this.status = AppStatus.AVAILABLE;
    }

}
