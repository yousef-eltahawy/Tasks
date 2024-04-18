package com.google.tasks.services.implementation;

import com.google.tasks.Repositories.EventReminderRepository;
import com.google.tasks.Repositories.EventRepository;
import com.google.tasks.dto.request.EventReminderDto;
import com.google.tasks.entities.Event;
import com.google.tasks.entities.EventReminder;
import com.google.tasks.exception.CustomException;
import com.google.tasks.mapper.EventMapper;
import com.google.tasks.response.SuccessResponse;
import com.google.tasks.services.interfaces.EventReminderService;
import com.google.tasks.util.ResponseIntegerKeys;
import com.google.tasks.util.ResponseStringKeys;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventReminderServiceImp implements EventReminderService {
     private final EventReminderRepository eventReminderRepository;
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final ModelMapper mapper;
    public EventReminderServiceImp(EventRepository eventRepository, EventReminderRepository eventReminderRepository
            , EventMapper eventMapper){

        this.eventRepository=eventRepository;
        this.eventReminderRepository=eventReminderRepository;
        this.eventMapper=eventMapper;
        mapper=new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true);
    }

    @Override
    public SuccessResponse<?> addReminder(String eventId, EventReminderDto dto){
        Optional<Event> optEvent=eventRepository.findById(eventId);
        if (!optEvent.isPresent())
            throw new CustomException(ResponseStringKeys.NOT_FOUND,ResponseIntegerKeys.NOT_FOUND);
        Event event=optEvent.get();
        EventReminder eventReminder=mapper.map(dto,EventReminder.class);
        event.getReminder().getOverrides().add(eventReminder);
        event=eventRepository.save(event);
        return new SuccessResponse<>(ResponseStringKeys.UPDATED, ResponseIntegerKeys.OK
                ,eventMapper.eventToEventDto(event));

    }
    @Override
    public SuccessResponse<?> updateReminder(Long eventReminderId, EventReminderDto dto){
        Optional<EventReminder> optEventReminder= eventReminderRepository.findById(eventReminderId);
        if(!optEventReminder.isPresent())
            throw new CustomException(ResponseStringKeys.NOT_FOUND, ResponseIntegerKeys.NOT_FOUND);
        EventReminder eventReminder=optEventReminder.get();
        eventReminder.setMethod(dto.getMethod());
        eventReminder.setMinutes(dto.getMinutes());
        eventReminderRepository.save(eventReminder);
        return new SuccessResponse<>(ResponseStringKeys.UPDATED, ResponseIntegerKeys.OK,dto);
    }
    @Override
    public SuccessResponse<?> deleteReminder(String eventId,Long eventReminderId){
        Optional<Event> optEvent=eventRepository.findById(eventId);
        if (!optEvent.isPresent())
            throw new CustomException(ResponseStringKeys.NOT_FOUND,ResponseIntegerKeys.NOT_FOUND);

        List<EventReminder> overrides= optEvent.get().getReminder().getOverrides();
        Optional<EventReminder> eventReminder=overrides.stream()
                .filter(r->r.getId().equals(eventReminderId)).findFirst();

        if(eventReminder.isPresent())
            overrides.remove(eventReminder.get());
        else
            throw new CustomException("there is no reminder with id : "+eventReminderId
                    +" belongs to this event",ResponseIntegerKeys.NOT_FOUND);

        eventRepository.save(optEvent.get());
        return new SuccessResponse<>(ResponseStringKeys.DELETED, ResponseIntegerKeys.OK);
    }
}
