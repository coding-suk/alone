package com.web.personalstudy.service;

import com.web.personalstudy.dto.schedule.SchedulePageResponseDto;
import com.web.personalstudy.dto.schedule.ScheduleRequestDto;
import com.web.personalstudy.dto.schedule.ScheduleResponseDto;
import com.web.personalstudy.entity.Schedule;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface ScheduleServiceImp {
    ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto);

    ScheduleResponseDto getSchedule(Long sid);

    Page<SchedulePageResponseDto> getSchedules(int page, int size);

    ScheduleResponseDto updateSchedule(Long sid, ScheduleRequestDto scheduleRequestDto);

    void deleteSchedule(Long sid);

    void addNewAssignees(Schedule schedule, Set<Long> assigneeIds);
}
