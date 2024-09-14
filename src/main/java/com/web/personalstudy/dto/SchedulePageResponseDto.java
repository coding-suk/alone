package com.web.personalstudy.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonIgnoreProperties({"number", "sort", "numberOfElements", "pageable", "empty"}) // JSON 응답에서 이 필드를 제외합니다
public class SchedulePageResponseDto {

    private Long sid;
    private String title;
    private String contents;
    private String createDate;
    private String updateDate;

}
