package com.google.tasks.mapper;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.tasks.Repositories.RemindersRepository;
import com.google.tasks.dto.request.EventDateAndTimeDto;
import com.google.tasks.dto.request.EventDto;
import com.google.tasks.dto.request.UpdateEventDto;
import com.google.tasks.entities.*;
import com.google.tasks.enums.EventColors;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class EventMapper {
    ModelMapper mapper ;
    RemindersRepository remindersRepository;
    public EventMapper(RemindersRepository repository){
        mapper=new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true);
        this.remindersRepository=repository;
    }
    public Event eventDtoToEvent(EventDto dto){
        if(dto == null)
            return null;
        Event event= mapper.map(dto,Event.class);
        event.getEventDateAndTime().setEvent(event);
        event.getAttendeeList().forEach(a->a.setEvent(event));
        return event;
    }
    public Event updateEvent(Event event, UpdateEventDto dto){
        if(dto != null){
            event.setSummary(dto.getSummary());
            event.setDescription(dto.getDescription());
            event.setGuestsCanInviteOthers(dto.getGuestsCanInviteOthers());
            event.setGuestsCanSeeOtherGuests(dto.getGuestsCanSeeOtherGuests());
            event.setRecurrence(dto.getRecurrence());
            event.setLocation(dto.getLocation());
            event.setColorId(dto.getColorId());
            if(dto.getEventDateAndTime() != null){
                EventDateAndTimeDto timeDto=dto.getEventDateAndTime();
                event.getEventDateAndTime().setStartDateTime(timeDto.getStartDateTime());
                event.getEventDateAndTime().setStartTimeZone(timeDto.getStartTimeZone());
                event.getEventDateAndTime().setEndDateTime(timeDto.getEndDateTime());
                event.getEventDateAndTime().setEndTimeZone(timeDto.getEndTimeZone());
                if(timeDto.getOriginalStartTimeDateTime() !=null){
                    event.getEventDateAndTime().setOriginalStartTimeDateTime(timeDto.getOriginalStartTimeDateTime());
                    event.getEventDateAndTime().setOriginalStartTimeTimeZone(timeDto.getOriginalStartTimeTimeZone());
                }
            }
        }
        return event;
    }
    public EventDto eventToEventDto(Event event){
        if(event== null)
            return null;
        return mapper.map(event,EventDto.class);
    }
    public List<EventDto> eventsToEventDtos(List<Event> events){

        return events.stream().map(this::eventToEventDto).collect(Collectors.toList());
    }
    public com.google.api.services.calendar.model.Event eventToGoogleEvent(Event event){
        if (event== null)
            return null;
        com.google.api.services.calendar.model.Event googleEvent
              = mapper.map(event, com.google.api.services.calendar.model.Event.class);

        googleEvent.setId(null);
        googleEvent.setColorId(String.valueOf(event.getColorId().ordinal()+1));
        googleEvent.setStatus(event.getStatus().toString());
        addEventDatesAndTimes(event,googleEvent);
        addEventReminders(event,googleEvent);
        addAttendees(event,googleEvent);
        return googleEvent;
    }
    public Organizer googlOrganizerToeEventOrganizer(com.google.api.services.calendar.model.Event.Organizer googleOrganizer){
        return mapper.map(googleOrganizer,Organizer.class);
    }
    private void addEventDatesAndTimes(Event event, com.google.api.services.calendar.model.Event googleEvent){
        EventDateAndTime eventDateAndTime=event.getEventDateAndTime();

        googleEvent.setStart(new EventDateTime()
                .setDateTime(new DateTime(eventDateAndTime.getStartDateTime()))
                .setTimeZone(eventDateAndTime.getStartTimeZone()));

        googleEvent.setEnd(new EventDateTime()
                .setDateTime(new DateTime(eventDateAndTime.getEndDateTime()))
                .setTimeZone(eventDateAndTime.getEndTimeZone()));

        if (eventDateAndTime.getOriginalStartTimeDateTime() != null) {
            googleEvent.setOriginalStartTime(new EventDateTime()
                    .setDateTime(new DateTime(eventDateAndTime.getOriginalStartTimeDateTime()))
                    .setTimeZone(eventDateAndTime.getOriginalStartTimeTimeZone()));
        }
    }
    private void addEventReminders(Event event, com.google.api.services.calendar.model.Event googleEvent){
        Optional<Reminders> reminders=remindersRepository.findById(event.getReminder().getId());
        List<com.google.api.services.calendar.model.EventReminder> overrides=new ArrayList<>();

        if(reminders.isPresent()){
            for(EventReminder r:reminders.get().getOverrides())
                overrides.add(new com.google.api.services.calendar.model.EventReminder()
                        .setMethod(r.getMethod().toString())
                        .setMinutes(r.getMinutes()));

            googleEvent.setReminders(new com.google.api.services.calendar.model.Event.Reminders()
                    .setOverrides(overrides)
                    .setUseDefault(event.getReminder().getUseDefault()));
        }
    }
    private void addAttendees(Event event, com.google.api.services.calendar.model.Event googleEvent){
        googleEvent.setAttendees(new ArrayList<>());
        event.getAttendeeList().forEach(a->{
            googleEvent.getAttendees().add(new EventAttendee()
                    .setAdditionalGuests(a.getAdditionalGuests())
                    .setComment(a.getComment())
                    .setDisplayName(a.getDisplayName())
                    .setEmail(a.getEmail())
                    .setOptional(a.getOptional())
                    .setOrganizer(a.getOrganizer())
                    .setSelf(a.getSelf()));
        });
    }
}