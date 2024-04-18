package com.google.tasks.controller;

import com.google.tasks.dto.request.EventDto;
import com.google.tasks.dto.request.PageRequestDto;
import com.google.tasks.dto.request.UpdateEventDto;
import com.google.tasks.response.SuccessResponse;
import com.google.tasks.response.SummaryPaginationResponse;
import com.google.tasks.services.interfaces.EventService;
import com.google.tasks.services.interfaces.GoogleEventService;
import com.google.tasks.util.ResponseIntegerKeys;
import com.google.tasks.util.ResponseStringKeys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("/event")
@Slf4j
public class EventController {
    @Autowired
    private EventService eventService;

    @Cacheable(value = "event_page")
    @GetMapping("/get_page")
    public SummaryPaginationResponse<?> getAllByPage(@RequestBody PageRequestDto request){
        log.info("getAllEventByPage() -> fetching from dataBase ");
        return eventService.getAll(request);

    }
    @Cacheable(value = "event",key ="#id")
    @GetMapping("/get")
    public SuccessResponse<?> getEvent(@RequestParam String id){
        log.info("getEvent() -> fetching from dataBase ");
        return eventService.getEvent(id);
    }

    @CacheEvict(value = "event_page",allEntries = true)
    @PostMapping("/create")
    public SuccessResponse<?> createEvent(@RequestBody EventDto dto) throws IOException, GeneralSecurityException {
        log.info("createEvent() -> fetching from dataBase ");
        return eventService.createEvent(dto);

    }
    @Caching(evict = @CacheEvict(value = "event_page",allEntries = true),put = @CachePut(value = "event",key = "#id"))
    @PutMapping("/update")
    public SuccessResponse<?> updateEvent(@RequestParam String id,@RequestBody UpdateEventDto dto) {
        log.info("updateEvent() -> fetching from dataBase ");
        return eventService.updateEvent(id,dto);

    }

    @Caching(evict = {@CacheEvict(value = "event" ,key = "#id"),@CacheEvict(value = "event_page",allEntries = true)})
    @DeleteMapping("/delete")
    public SuccessResponse<?> delete(@RequestParam String id){
        log.info("eventDelete() -> fetching from dataBase ");
        return eventService.deleteEvent(id);
    }
}
