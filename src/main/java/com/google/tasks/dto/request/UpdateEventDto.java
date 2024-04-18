package com.google.tasks.dto.request;

import com.google.tasks.enums.EventColors;
import lombok.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventDto implements Serializable {
    private String summary;
    private String description;
    private Boolean guestsCanInviteOthers;
    private Boolean guestsCanSeeOtherGuests;
    private String location;
    private List<String> recurrence;
    private EventColors colorId;

    private EventDateAndTimeDto eventDateAndTime;
}
