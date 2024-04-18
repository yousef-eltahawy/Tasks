package com.google.tasks.controller;

import com.google.tasks.dto.request.EventReminderDto;
import com.google.tasks.response.SuccessResponse;
import com.google.tasks.services.interfaces.EventReminderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/event/reminder")
public class EventReminderController {
    @Autowired
    private EventReminderService service;

    @Caching(evict = {@CacheEvict(value = "event" ,key = "#eventId"),@CacheEvict(value = "event_page",allEntries = true)})
    @PostMapping("/add")
    public SuccessResponse<?> addEventReminder(@RequestParam String eventId, @RequestBody EventReminderDto dto){
        log.info("addEventReminder() -> fetching from dataBase ");
        return service.addReminder(eventId,dto);
    }
    @Caching(evict = {@CacheEvict(value = "event" ,allEntries = true),@CacheEvict(value = "event_page",allEntries = true)})
    @PutMapping("/update")
    public SuccessResponse<?> updateEventReminder(@RequestParam Long eventReminderId, @RequestBody EventReminderDto dto){
        log.info("updateEventReminder() -> fetching from dataBase ");
        return service.updateReminder(eventReminderId,dto);
    }
    @Caching(evict = {@CacheEvict(value = "event" ,key = "#eventId"),@CacheEvict(value = "event_page",allEntries = true)})
    @DeleteMapping("/delete")
    public SuccessResponse<?> deleteEventReminder(@RequestParam String eventId,@RequestParam Long eventReminderId){
        log.info("deleteEventReminder() -> fetching from dataBase ");
        return service.deleteReminder(eventId,eventReminderId);
    }

}
