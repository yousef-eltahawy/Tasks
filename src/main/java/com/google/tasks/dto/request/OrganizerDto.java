package com.google.tasks.dto.request;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrganizerDto implements Serializable {
    private String displayName;
    private String email;
    private Boolean self;
}
