package com.google.tasks.services.interfaces;

import com.google.tasks.dto.request.EventReminderDto;
import com.google.tasks.response.SuccessResponse;

public interface EventReminderService {
    SuccessResponse<?> addReminder(String eventId, EventReminderDto dto);

    SuccessResponse<?> updateReminder(Long eventReminderId, EventReminderDto dto);

    SuccessResponse<?> deleteReminder(String eventId,Long eventReminderId);
}
