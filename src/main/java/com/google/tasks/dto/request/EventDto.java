package com.google.tasks.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.api.client.util.DateTime;
import com.google.tasks.enums.EventColors;
import com.google.tasks.enums.EventStatus;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventDto implements Serializable {
    private String id;
    private String summary;
    private Boolean anyoneCanAddSelf;
    private Boolean attendeesOmitted;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDateAndTime;
    private String description;
    private Boolean endTimeUnspecified;
    private String etag;
    private String eventType;
    private Boolean guestsCanInviteOthers;
    private Boolean guestsCanModify;
    private Boolean guestsCanSeeOtherGuests;
    private String hangoutLink;
    private String location;
    private Boolean locked;
    private Boolean privateCopy;
    private List<String> recurrence;
    private String recurringEventId;
    private Integer sequence;
    private String visibility;
    private String transparency;
    private EventColors colorId;
    private EventStatus status;
    private OrganizerDto organizer;
    private RemindersDto reminder;
    private EventDateAndTimeDto eventDateAndTime;
    private List<AttendeeDto> attendeeList;
}
