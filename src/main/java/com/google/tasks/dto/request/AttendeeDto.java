package com.google.tasks.dto.request;

import lombok.*;

import java.io.Serializable;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttendeeDto implements Serializable {
    private Long id;
    private Integer additionalGuests;
    private String comment;
    private String displayName;
    private String email;
    private Boolean optional;
    private Boolean organizer;
    private Boolean self;

}
