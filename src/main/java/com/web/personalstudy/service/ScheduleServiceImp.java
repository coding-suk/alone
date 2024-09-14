package com.web.personalstudy.service;

import com.web.personalstudy.dto.SchedulePageResponseDto;
import com.web.personalstudy.dto.ScheduleRequestDto;
import com.web.personalstudy.dto.ScheduleResponseDto;
import org.springframework.data.domain.Page;

public interface ScheduleServiceImp {
    ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto);

    ScheduleResponseDto getSchedule(Long sid);

    Page<SchedulePageResponseDto> getSchedules(int page, int size);

    ScheduleResponseDto updateSchedule(Long sid, ScheduleRequestDto scheduleRequestDto);

    void deleteSchedule(Long sid);
}
