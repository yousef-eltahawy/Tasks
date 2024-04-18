package com.google.tasks.services.implementation;

import com.google.tasks.Repositories.AttendeeRepository;
import com.google.tasks.Repositories.EventRepository;
import com.google.tasks.dto.request.AttendeeDto;
import com.google.tasks.entities.Attendee;
import com.google.tasks.entities.Event;
import com.google.tasks.exception.CustomException;
import com.google.tasks.response.SuccessResponse;
import com.google.tasks.services.interfaces.AttendeeService;
import com.google.tasks.util.ResponseIntegerKeys;
import com.google.tasks.util.ResponseStringKeys;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AttendeeServiceImp implements AttendeeService{
    private final AttendeeRepository repository;
    private final EventRepository eventRepository;
    private final ModelMapper mapper;
    public AttendeeServiceImp(AttendeeRepository repository,EventRepository eventRepository){
        this.repository=repository;
        this.eventRepository=eventRepository;
        mapper=new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true);
    }
    @Override
    public SuccessResponse<?> addAttendee(String eventId, AttendeeDto dto){
        Optional<Event> optEvent=eventRepository.findById(eventId);
        if (!optEvent.isPresent())
            throw new CustomException(ResponseStringKeys.NOT_FOUND,ResponseIntegerKeys.NOT_FOUND);
        Attendee attendee=mapper.map(dto,Attendee.class);
        optEvent.get().getAttendeeList().add(attendee);
        attendee.setEvent(optEvent.get());
        eventRepository.save(optEvent.get());
        return new SuccessResponse<>(ResponseStringKeys.OK, ResponseIntegerKeys.OK,dto);
    }
    @Override
    public SuccessResponse<?> deleteAttendee(String eventId,Long id){
        Optional<Event> optEvent=eventRepository.findById(eventId);
        if (!optEvent.isPresent())
            throw new CustomException(ResponseStringKeys.NOT_FOUND,ResponseIntegerKeys.NOT_FOUND);
        Optional<Attendee> optAttendee=repository.findById(id);
        if(!optAttendee.isPresent())
            throw new CustomException(ResponseStringKeys.NOT_FOUND,ResponseIntegerKeys.NOT_FOUND);
        optEvent.get().getAttendeeList().forEach(a->{
            if(a.getId()==id)
                optEvent.get().getAttendeeList().remove(a);
        });
        eventRepository.save(optEvent.get());
        return new SuccessResponse<>(ResponseStringKeys.DELETED, ResponseIntegerKeys.OK);
    }
}
