package com.web.personalstudy.controller;

import com.web.personalstudy.dto.ScheduleResponseDto;
import com.web.personalstudy.dto.ScheduleRequestDto;
import com.web.personalstudy.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ScheduleController {

    private final ScheduleService scheduleService;

    /**
    * 새로운 일정 등록
    *
    * @Param scheduleResquestDto 일정 등록 요청 DTo
    * @return 생성된 일정 응답 DTO 와 HTTP 상태 코드 200(ok)
    */
    @PostMapping(value="/v1/schedules", produces="application/json")
    public  ResponseEntity<ApiResponse<ScheduleResponseDto>> register(@RequestBody ScheduleRequestDto scheduleRequestDto) {
        ScheduleResponseDto scheduleResponseDto = scheduleService.createSchedule(scheduleRequestDto);
        try{
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(scheduleResponseDto));
        } catch (Exception e) {
            LoggerUtil.logError("일정 생성", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.erroe("일정 생성 실패", e.getMessage()));

        }
    }

    /**
     * ID로 특정 일정을 조회
     *
     * @Param sid 일정의 ID
     * @return 조회된 일정 응답 DTO를 포함한 HTTP 상태 코드 200(ok) 또는 일정이 없을 경우 HTTP 상태 코드 404(Not Found)
     * */
    @GetMapping("/v1/schedules/{sid}")
    public ResponseEntity<ApiResponse<ScheduleResponseDto>> getSchedule(@PathVariable Long sid) {
        Optional<ScheduleResponseDto> shcedule = Optional.ofNullable(scheduleService.getSchedule(sid));
        return shcedule.map(dto -> {
            LoggerUtil.logInfo("getSchedule", dto);
            return ResponseEntity.ok(ApiResponse.success(dto));
        }).orElseGet(()-> {
            LoggerUtil.logError("일정 조회", new NoSuchElementException("일정을 찾을 수 없습니다"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("일정을 찾을 수 없습니다", "일정 ID: " + sid + "에 해당하는 일정이 존재하지 않습니다"))
        })
    }

}
