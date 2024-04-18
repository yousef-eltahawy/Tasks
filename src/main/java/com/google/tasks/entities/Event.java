package com.google.tasks.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.tasks.enums.EventColors;
import com.google.tasks.enums.EventStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public class Event implements Serializable {
    @Id
    private String id;
    @Column(unique = true,nullable = false)
    private String summary;
    private Boolean anyoneCanAddSelf;
    private Boolean attendeesOmitted;
    @CreatedDate
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
    private boolean isWaiting;
    private String visibility;
    private String transparency;
    @Enumerated(EnumType.STRING)
    private EventColors colorId;
    @Enumerated(EnumType.STRING)
    private EventStatus status;
    @OneToOne(cascade = CascadeType.ALL)
    private Organizer organizer;
    @OneToOne(cascade =CascadeType.ALL)
    private Reminders reminder;
    @OneToOne(mappedBy = "event",cascade = CascadeType.ALL)
    private EventDateAndTime eventDateAndTime;
    @OneToMany(mappedBy = "event",cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
    private List<Attendee> attendeeList;


}
