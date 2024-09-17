package com.web.personalstudy.service;

import com.web.personalstudy.dto.schedule.SchedulePageResponseDto;
import com.web.personalstudy.dto.schedule.ScheduleResponseDto;
import com.web.personalstudy.dto.schedule.ScheduleRequestDto;
import com.web.personalstudy.entity.Schedule;
import com.web.personalstudy.entity.ScheduleAssignees;
import com.web.personalstudy.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService implements  ScheduleServiceImp{

    private final ScheduleRepository scheduleRepository;

    /**
     * 새로운 일정을 생성하는 메서드
     * 일정의 수유자와 담당자를 추가
     * @Param scheduleRequestDto 일정 요청 DTO
     * @return 생성된 일정의 응답DTO
     */
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

    /**
     * 일정 ID로 특정 일정을 조회하는 메서드
     * 담당자가 없는 경우 일정을 조회 할수 없게 합니다
     *
     * @Param sid 일정 ID
     * @return 조회된 일정의 응답 DTO(Optional)
     * */
    @Transactional(readOnly = true)
    @Override
    public ScheduleResponseDto getSchedule(Long sid) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(sid);
        if(schedule.getAssignees() == null || schedule.getAssignees().isEmpty()) {
            throw new IllegalStateException("담당자가 비어있어 스케줄을 조회 할수 없습니다");
        }
        return ScheduleResponseDto.from(schedule);
    }

    /**
     * 모든 일정을 페이지 단위로 조회하는 메서드입니다
     *
     * @Param page 페이지 번호
     * @Param size 페이지 크기
     * @Return 일정 페이지 응답 DTO
     * */
    @Transactional(readOnly = true)
    @Override
    public Page<SchedulePageResponseDto> getSchedules(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "updateDate"));
        return scheduleRepository.findAll(pageable).map(SchedulePageResponseDto ::from);
    }

    /**
     * 특정 일정을 수정하는 메서드
     * 일정의 소유자와 담당자 목록을 업데이트합니다
     *
     * @Param sid 일정 ID
     * @Param scheduleRequestDto 일정 요청 DTO
     * @return 수정된 일정의 응답 DTO
     * */
    @Override
    @Transactional
    public ScheduleResponseDto updateSchedule(Long sid, ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(sid);
        if(scheduleRequestDto.getOwnerId() != null) {
            User newOwner = userRepository.findByIdOrElseThrow(scheduleRequestDto.getOwnerId());
            schedule.updateOwner(newOwner); // 메소드를 통해 업데이트
        }
        //  Schedule 객체의 필드 업데이트
        schedule.updateSchedule(scheduleRequestDto);
        // 새로운 담당자 추가 // 로직 변경
        // 새로운 담당자 추가
        if(scheduleRequestDto.getAssigneeIds() != null) {
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
