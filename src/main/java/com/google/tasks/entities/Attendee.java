package com.google.tasks.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Attendee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer additionalGuests;
    private String comment;
    private String displayName;
    private String email;
    private Boolean optional;
    private Boolean organizer;
    private Boolean self;
    @ManyToOne
    @JoinColumn(name = "eventId")
    private Event event;
}
