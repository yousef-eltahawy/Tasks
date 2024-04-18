package com.google.tasks.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.api.client.util.DateTime;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;


import java.io.Serializable;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EventDateAndTime implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String endDate;
    @Column(nullable = false)
    private String  endDateTime;
    @Column(nullable = false)
    private String endTimeZone;
    private String startDate;
    @Column(nullable = false)
    private String startDateTime;
    @Column(nullable = false)
    private String startTimeZone;
    private String originalStartTimeDate;
    private String originalStartTimeDateTime;
    private String originalStartTimeTimeZone;
    @OneToOne
    @JoinColumn(name = "event_id")
    private Event event;

}
