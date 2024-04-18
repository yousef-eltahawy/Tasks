package com.google.tasks.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageRequestDto {
    private int pageNumber;
    private int size;
}
