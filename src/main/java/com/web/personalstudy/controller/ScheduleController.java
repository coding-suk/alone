package com.web.personalstudy.controller;

import com.web.personalstudy.common.response.ApiResponse;
import com.web.personalstudy.common.util.LoggerUtil;
import com.web.personalstudy.dto.schedule.SchedulePageResponseDto;
import com.web.personalstudy.dto.schedule.ScheduleResponseDto;
import com.web.personalstudy.dto.schedule.ScheduleRequestDto;
import com.web.personalstudy.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
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
                    .body(ApiResponse.error("일정 생성 실패", e.getMessage()));

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
                    .body(ApiResponse.error("일정을 찾을 수 없습니다", "일정 ID: " + sid + "에 해당하는 일정이 존재하지 않습니다"));
        });
    }

    /**
     * 페이지 네이션을 통해 모든 일정을 조회
     *
     * @Param page조회할 페이지 번호 (기본값 : 0)
     * @Param size 페이지당 항목 수 (기본 값: 10)
     * @return 일정 응답 DTO의 페이지 목록을 포함한 HTTP상태 코드 200(ok)
     */

    @GetMapping("/v1,schedules")
    public ResponseEntity<ApiResponse<Page<SchedulePageResponseDto>>> getSchedules(@RequestParam(defaultValue = "0") int page,
                                                                               @RequestParam(defaultValue = "10") int size) {
        Page<SchedulePageResponseDto> schedule = scheduleService.getSchedules(page, size);
        return ResponseEntity.ok(ApiResponse.success(schedule));
    }

    /**
     * 특정 일정을 업데이트 합니다
     *
     * @Parma sid 일절의 ID
     * @Param scheduleRequestDto 업데이트 할 일정 요청 DTO
     * @return 엡이트된 일정 응답 DTO와 HTTP 상태 코드 200(ok)
     */
    @PutMapping("/v1/schedule/{sid}")
    public ResponseEntity<ApiResponse<ScheduleResponseDto>> updateSchedule(@PathVariable Long sid,
                                                                           @RequestBody ScheduleRequestDto scheduleRequestDto) {
        try {
            ScheduleResponseDto updatedSchedule = scheduleService.updateSchedule(sid, scheduleRequestDto);
            return ResponseEntity.ok(ApiResponse.success(updatedSchedule));
        }
        catch (Exception e) {
            LoggerUtil.logError("일정 업데이트 실패", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("일정 업데이트 실패", e.getMessage()));
        }
    }

    /**
     * 특정 일정을 삭제
     *
     * @Param sid 일정의 ID
     * @return HTTP 상태 코드 204(No Content)
     * */
    @DeleteMapping("/v1/schedules/{sid}")
    public ResponseEntity<ApiResponse<ScheduleResponseDto>> deleteSchedule(@PathVariable long sid) {
        try {
            scheduleService.deleteSchedule(sid);
            return ResponseEntity.noContent().build(); // No Content는 본문이 없으므로, ApiResponse 사용 필요x
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("일정 삭제 실패", e.getMessage()));
        }
    }

}
