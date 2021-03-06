# Even Sourcing Demo
## Description
Keep in mind that I'm not very good at explaining things and let's consider that I'm not going to give more deail about even sourcing concept becuase this is just a demo, I'm just going to share a summary of things I did and you should go deeper to understand them even better. Before talking about event sourcing, let's consider taking a look at DDD, because even sourcing is related to that. 

Once we have defined our aggregate (aggregate would be "a cluster of domain objects that can be treated as a single unit", an example may be an order, address and order item , these will be separate objects, but it's useful to treat as a single aggregate) state changes will occur for this agrgregate, which means that every change of the state of our application need to be persisted. And then is when even sourcing takes place to atomically update state and publish events. Event sourcing is driven by events, and those events are going to rely on the business case.

We are going to use for this demo Axon framework which is a good choice for microservcies and its integration with spring boot is quite simple.

## Project settings
We are going to work with this stack of dependencies:
* Spring boot
* Spring data jpa
* H2 database
* Swagger 2


## Test app
Once you have cloned this app, you just need to run: clean package spring-boot:run

Once the application is up and running, you should be able to reach this url: http://localhost:8080/swagger-ui.html
There, you are going to see the list of the endpoints availables in the app and you should be able to try them out.

###### Steps

`We are going to use below the following appointment id (28b8f2dd-62b8-4857-8d90-4586ae656118) just to illustrate it much better. If you try this out, you should use the id returned by the service.`

###### Post
When the post is executed two events are going to be triggered, the first one in order to create a new appointment with the status new and the second one in order to set it as available. 

Example:
```
curl -X POST "http://localhost:8080/appointment?endDate=2020-04-19T10%3A00%3A00&provider=HR%20interviewer&startDate=2020-04-19T09%3A00%3A00&user=Luis" -H "accept: */*"
```
###### Put / start 
In this update we are going to start it up.

Example:
```
curl -X PUT "http://localhost:8080/appointment/start/{appointmentId}?appointmentId=28b8f2dd-62b8-4857-8d90-4586ae656118" -H "accept: */*"
```
And after starting the appointment another event is triggered in order to setup the appointment's status to "in progress" 

###### Put / book
When we select an appointment we may book it by using the id.

Example:
```
curl -X PUT "http://localhost:8080/appointment/book/{appointmentId}?appointmentId=28b8f2dd-62b8-4857-8d90-4586ae656118" -H "accept: */*"
```

###### Put / cancel
When the appointment is not considered anymore it can be cancelled.

Example:
```
curl -X PUT "http://localhost:8080/appointment/cancel/{appointmentId}?appointmentId=28b8f2dd-62b8-4857-8d90-4586ae656118" -H "accept: */*"
```
After cancelling it the an event will be triggered in order to finish the appointment.

###### Put / complete appointment
When the appointment finish or it is cancelled this endpoint is processed.

Example:
```
curl -X PUT "http://localhost:8080/appointment/complete/{appointmentId}?appointmentId=28b8f2dd-62b8-4857-8d90-4586ae656118" -H "accept: */*"
```


###### Validation
The previous action can be verified by using the h2 consoles. So, by visiting http://localhost:8080/h2-console you should be able to open the h2 console up in order to check the database in memory. When the console come up, in JDBC url use this one: jdbc:h2:mem:testdb. The other parameters are ok.

Once you are in, please perform this query:
SELECT PAYLOAD_TYPE , AGGREGATE_IDENTIFIER, SEQUENCE_NUMBER , PAYLOAD   FROM DOMAIN_EVENT_ENTRY 
And you are going to see the events described before.


