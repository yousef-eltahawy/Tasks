package com.google.tasks.services.interfaces;

import com.google.tasks.dto.request.EventDto;
import com.google.tasks.dto.request.PageRequestDto;
import com.google.tasks.dto.request.UpdateEventDto;
import com.google.tasks.response.SuccessResponse;
import com.google.tasks.response.SummaryPaginationResponse;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface EventService {
    SummaryPaginationResponse<?> getAll(PageRequestDto dto);

    SuccessResponse<?> getEvent(String id);

    SuccessResponse<?> createEvent(EventDto dto) throws IOException, GeneralSecurityException;

    SuccessResponse<?> updateEvent(String id, UpdateEventDto dto);

    SuccessResponse<?> deleteEvent(String id);
}
