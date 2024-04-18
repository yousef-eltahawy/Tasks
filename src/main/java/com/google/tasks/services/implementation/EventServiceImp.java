package com.google.tasks.services.implementation;

import com.google.tasks.Repositories.EventRepository;
import com.google.tasks.config.CheckInternetReachability;
import com.google.tasks.dto.request.EventDto;
import com.google.tasks.dto.request.PageRequestDto;
import com.google.tasks.dto.request.UpdateEventDto;
import com.google.tasks.entities.Event;
import com.google.tasks.entities.Organizer;
import com.google.tasks.exception.CustomException;
import com.google.tasks.mapper.EventMapper;
import com.google.tasks.response.SuccessResponse;
import com.google.tasks.response.SummaryPaginationResponse;
import com.google.tasks.services.interfaces.EventService;
import com.google.tasks.services.interfaces.GoogleEventService;
import com.google.tasks.util.ResponseIntegerKeys;
import com.google.tasks.util.ResponseStringKeys;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.InetAddress;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventServiceImp implements EventService {
    private final EventMapper mapper;
    private final EventRepository repository;
    private final GoogleEventService googleEventService;

    public EventServiceImp(EventMapper mapper,EventRepository repository,GoogleEventService googleEventService){
        this.mapper=mapper;
        this.repository=repository;
        this.googleEventService=googleEventService;

    }
    @Override
    public SummaryPaginationResponse<?> getAll(PageRequestDto dto){
        Page<Event> page=repository.findAll(PageRequest.of(dto.getPageNumber(),dto.getSize()));
        List<EventDto> eventDtos=mapper.eventsToEventDtos(page.getContent());
        return new SummaryPaginationResponse<>(ResponseStringKeys.OK,ResponseIntegerKeys.OK,
                eventDtos,
                page.getTotalElements(),
                page.getTotalPages(),
                page.getSize(),
                page.getSize()
                );
    }
    @Override
    public SuccessResponse<?> getEvent(String id){
        Optional<Event> optEvent=repository.findById(id);
        if(!optEvent.isPresent())
            throw new CustomException(ResponseStringKeys.NOT_FOUND, ResponseIntegerKeys.NOT_FOUND);
        return new SuccessResponse<>(ResponseStringKeys.OK,ResponseIntegerKeys.OK
                ,mapper.eventToEventDto(optEvent.get()));
    }
    @Override
    public SuccessResponse<?> createEvent(EventDto dto) throws IOException, GeneralSecurityException {
        Optional<Event> optEvent=repository.findBySummary(dto.getSummary());
        if(optEvent.isPresent())
            throw new CustomException("this event already exists", ResponseIntegerKeys.SQL_EXC);

        com.google.api.services.calendar.model.Event googleEvent;
        Event event= mapper.eventDtoToEvent(dto);
        event.setId(UUID.randomUUID().toString().replace("-","").substring(0,8));
        event=repository.save(event);
        try {
            googleEvent=googleEventService.createNewEvent(event);
            event.setOrganizer(mapper.googlOrganizerToeEventOrganizer(googleEvent.getOrganizer()));

        } catch (IOException | GeneralSecurityException ex) {
            CheckInternetReachability.eventIsWaiting=true;
            event.setWaiting(true);
        }

        event=repository.save(event);

        return new SuccessResponse<>(ResponseStringKeys.OK,ResponseIntegerKeys.OK
                ,mapper.eventToEventDto(event));
    }
    @Override
    public SuccessResponse<?> updateEvent(String id, UpdateEventDto dto){
        Optional<Event> optEvent=repository.findById(id);
        if(!optEvent.isPresent())
            throw new CustomException(ResponseStringKeys.NOT_FOUND, ResponseIntegerKeys.NOT_FOUND);
        Event event=optEvent.get();
        event=mapper.updateEvent(event,dto);
        event=repository.save(event);
        return new SuccessResponse<>(ResponseStringKeys.OK,ResponseIntegerKeys.OK,mapper.eventToEventDto(event));
    }
    @Override
    public SuccessResponse<?> deleteEvent(String id){
        Optional<Event> optEvent=repository.findById(id);
        if(!optEvent.isPresent())
            throw new CustomException(ResponseStringKeys.NOT_FOUND, ResponseIntegerKeys.NOT_FOUND);
        repository.deleteById(id);
        return new SuccessResponse<>(ResponseStringKeys.DELETED,ResponseIntegerKeys.OK);

    }
}
