package com.lab.aggregates;

import java.time.LocalDateTime;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import com.lab.command.BookAppointmentCommand;
import com.lab.command.CancelAppointmentCommand;
import com.lab.command.CreateAppointmentCommand;
import com.lab.command.FinishAppointmentCommand;
import com.lab.command.StartAppointmentCommand;
import com.lab.event.AppointmentAvailableEvent;
import com.lab.event.AppointmentBookedEvent;
import com.lab.event.AppointmentCancelledEvent;
import com.lab.event.AppointmentCreatedEvent;
import com.lab.event.AppointmentFinishedEvent;
import com.lab.event.AppointmentInProgressEvent;

@Aggregate
@SuppressWarnings("unused")
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
	
	
	@CommandHandler
	public void on(BookAppointmentCommand bookAppointmentCommand) {
		AggregateLifecycle.apply(new AppointmentBookedEvent(bookAppointmentCommand.id));
	}
	
	@EventSourcingHandler
	public void on(AppointmentBookedEvent appointmentBookedEvent) {
		this.status = appointmentBookedEvent.status;
	}
	
	@CommandHandler
	public void on(StartAppointmentCommand startAppointmentCommand) {
		AggregateLifecycle.apply(new AppointmentInProgressEvent(startAppointmentCommand.id));
	}
	
	@EventSourcingHandler
	public void on(AppointmentInProgressEvent appointmentInProgressEvent) {
		this.status = appointmentInProgressEvent.status;
	}
	
	@CommandHandler
	public void on(FinishAppointmentCommand finishAppointmentCommand) {
		AggregateLifecycle.apply(new AppointmentFinishedEvent(finishAppointmentCommand.id));
	}
	
	@EventSourcingHandler
	public void on(AppointmentFinishedEvent appointmentFinishedEvent) {
		this.status = appointmentFinishedEvent.status;
	}
	
	@CommandHandler
	public void on(CancelAppointmentCommand cancelAppointmentCommand) {
		AggregateLifecycle.apply(new AppointmentCancelledEvent(cancelAppointmentCommand.id));
	}
	
	@EventSourcingHandler
	public void on(AppointmentCancelledEvent appointmentCancelledEvent) {
		this.status = appointmentCancelledEvent.status;
		AggregateLifecycle.apply(new AppointmentFinishedEvent(appointmentCancelledEvent.id));
	}

}
