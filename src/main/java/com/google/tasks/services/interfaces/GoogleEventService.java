package com.google.tasks.services.interfaces;



import com.google.api.services.calendar.model.Event;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface GoogleEventService {
    Event createNewEvent(com.google.tasks.entities.Event event) throws IOException, GeneralSecurityException;
}
