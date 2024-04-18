package com.google.tasks.services.interfaces;

import com.google.tasks.dto.request.AttendeeDto;
import com.google.tasks.response.SuccessResponse;

public interface AttendeeService {
    SuccessResponse<?> addAttendee(String eventId, AttendeeDto dto);

    SuccessResponse<?> deleteAttendee(String eventId,Long id);
}
