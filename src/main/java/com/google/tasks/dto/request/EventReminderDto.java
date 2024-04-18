package com.google.tasks.dto.request;

import com.google.tasks.enums.ReminderMethods;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventReminderDto {
    private Long id;
    private ReminderMethods method;
    private Integer minutes;
}
