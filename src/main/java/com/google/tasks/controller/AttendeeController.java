package com.google.tasks.controller;

import com.google.tasks.dto.request.AttendeeDto;
import com.google.tasks.response.SuccessResponse;
import com.google.tasks.services.interfaces.AttendeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("event/attendee")
@Slf4j
public class AttendeeController {

    @Autowired
    private AttendeeService service;


    @Caching(evict = {@CacheEvict(value = "event" ,key = "#eventId"),@CacheEvict(value = "event_page",allEntries = true)})
    @PostMapping("/add")
    public SuccessResponse<?> addAttendee(@RequestParam String eventId,@RequestBody AttendeeDto dto){
        log.info("addAttendee() -> fetching from dataBase ");
        return service.addAttendee(eventId,dto);
    }
    @Caching(evict = {@CacheEvict(value = "event" ,key = "#eventId"),@CacheEvict(value = "event_page",allEntries = true)})
    @DeleteMapping("/delete")
    public SuccessResponse<?> deleteAttendee(@RequestParam String eventId,@RequestParam Long attendeeId){
        log.info("deleteAttendee() -> fetching from dataBase ");
        return service.deleteAttendee(eventId,attendeeId);
    }
}
