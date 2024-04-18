package com.google.tasks.config;

import com.google.tasks.Repositories.EventRepository;
import com.google.tasks.entities.Event;
import com.google.tasks.mapper.EventMapper;
import com.google.tasks.services.interfaces.GoogleEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.InetAddress;
import java.security.GeneralSecurityException;
import java.util.List;

@Component
@Slf4j
public class CheckInternetReachability {
    public static boolean eventIsWaiting;
    private GoogleEventService googleEventService;
    private EventRepository eventRepository;
    private EventMapper mapper;

    public CheckInternetReachability(GoogleEventService service, EventRepository repository, EventMapper mapper) {
        this.googleEventService = service;
        this.eventRepository = repository;
        this.mapper = mapper;
    }


    @Scheduled(fixedRate = 3000)
    private void checkReachability() {

        log.info("CheckReachability Invoked, " + "eventIsWaiting : " + eventIsWaiting);
        if (eventIsWaiting) {
            try {
                InetAddress inetAddress = InetAddress.getByName("google.com");
                if (inetAddress.isReachable(2000)) {
                    log.info("Is Reachable : true");
                    createWaitingEvents();
                }
            } catch (IOException | GeneralSecurityException ex) {
                throw new RuntimeException(ex);
            }
            eventIsWaiting = false;
        }
    }


    private void createWaitingEvents() throws IOException,GeneralSecurityException {
        List<Event> events= eventRepository.findAllByIsWaiting(true);
        if (!events.isEmpty())
            events.forEach(e-> {
                try {
                    e.setOrganizer(mapper.googlOrganizerToeEventOrganizer(
                            googleEventService.createNewEvent(e).getOrganizer()));
                    e.setWaiting(false);
                    eventRepository.save(e);
                } catch (IOException | GeneralSecurityException ex) {
                    throw new RuntimeException(ex);
                }
            });
    }


}
