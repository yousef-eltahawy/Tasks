package com.google.tasks.dto.request;

import com.google.tasks.entities.EventReminder;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RemindersDto implements Serializable {

    private List<EventReminderDto> overrides;

    private Boolean useDefault;
}
