package com.web.personalstudy.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
public class ScheduleRequestDto {

    private String title;
    private String contents;
    private Set<Long> assigneeIds;

    @Builder
    public ScheduleRequestDto(String title, String contents, Set<Long> assigneeIds) {
        this.title = title;
        this.contents = contents;
        this.assigneeIds = assigneeIds;
    }

}
