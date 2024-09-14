package com.web.personalstudy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {

    private Long sid;
    private String title;
    private String contents;
    private String createdDate;
    private String updateDate;

}
