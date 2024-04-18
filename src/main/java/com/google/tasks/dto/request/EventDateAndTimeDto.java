package com.google.tasks.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.api.client.util.DateTime;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventDateAndTimeDto {
    private String endDate;
    private String endDateTime;
    private String endTimeZone;
    private String startDate;
    private String startDateTime;
    private String startTimeZone;
    private String originalStartTimeDate;
    private String originalStartTimeDateTime;
    private String originalStartTimeTimeZone;

}
