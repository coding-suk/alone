package com.web.personalstudy.service;

import com.web.personalstudy.dto.ScheduleResponseDto;
import com.web.personalstudy.dto.ScheduleRequestDto;
import com.web.personalstudy.entity.Schedule;
import com.web.personalstudy.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ScheduleService implements  ScheduleServiceImp{

    private final ScheduleRepository scheduleRepository;

    @Transactional
    @Override
    public ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto) {

        Schedule schedule = Schedule.builder()
                .title(scheduleRequestDto.getTitle())
                .contents(scheduleRequestDto.getContents())
                .build();
        if (scheduleRequestDto.getAssigneeIds() != null) {
            addNewAssignees(schedule, scheduleRequestDto.getAssigneeIds());
        }
        return ScheduleResponseDto.from(scheduleRepository.save(schedule));
    }

    @Override
    private void addNewAssignees(Schedule schedule, Set<Long> assigneeIds) {
        // 현재 담당자들의 ID를 수집
        Set<Long> currentAssigneeIds = schedule.getAssignees().stream()
                .map(assignee -> assignee.getUser().getUid())
                .collect(Collerctors.toSet());

        // 새로 추가해야 할 담당자 ID
        Set<Long> newAssigneeIds = new HashSet<>(assigneeIds);
        newAssigneeIds.removeAll(currentAssigneeIds);

        //삭제해야 할 담당자
        Set<ScheduleAssignees> toRemove = schedule.getAssignees().stream()
                .filter(assignee -> !assigneeIds.contains(assignee.getUser().getUid()))
                .collect(Collectors.toSet());

        //삭제 처리
        schedule.getAssignees().removeAll(toRemove);

        //새로 추가해야할 담당자 처리
        for (Long AssigneeId : newAssigneeIds) {
            User assignee = userRepository.findByIdOrElseThrow(assigneeId);
            schedule.addAssignedUser(assignee);
        }
    }

}
