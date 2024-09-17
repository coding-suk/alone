package com.web.personalstudy.controller;

import com.web.personalstudy.common.response.ApiResponse;
import com.web.personalstudy.dto.ReplyRequestDto;
import com.web.personalstudy.dto.ReplyResponseDto;
import com.web.personalstudy.service.ReplyService;
import com.web.personalstudy.ㅇ새.ReplyRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedules")
@Slf4j
public class ReplyController {

    private final ReplyService replyService;

    /**
     * 새로운 댓글을 생성함
     * @Param sid 댓글이 달릴 일정의 ID
     * @Param replyRequestDto 댓글 요청 DTO
     * @return 조회된 댓글 응답 DTO를 포함한 HTTP 상태 코드 200(ok)
     * */
    @PostMapping("/{sid}/replies")
    public ResponseEntity<ApiResponse<ReplyResponseDto>> createReply(@PathVariable long sid,
                                                                     @RequestBody ReplyRequestDto replyRequestDto) {
        try{
            ReplyResponseDto createReply = replyService.addReply(sid, replyRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(createReply));
        } catch (Exception e) {
            LoggerUtil.logError("댓글 생성 실패: {}", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("REPLY_CREATION_FAILED", "댓글 생성 실패: "+ e.getMessage()));
        }
    }

    /**
     * 일정 ID로 모든 댓글을 조회함
     *
     * @Param sid 일정의 ID
     * @return 조회된 댓글 응답 DTO 목록을 포함한 HTTP 상태 코드 200(ok)
     * */
    @GetMapping("/{sid}/replies")
    public ResponseEntity<ApiResponse<List<ReplyResponseDto>>> getReplies(@PathVariable long sid) {
        try {
            List<ReplyResponseDto> replies = replyService.getReplies(sid);
            return ResponseEntity.ok(ApiResponse.success(replies));
        } catch (Exception e) {
            LoggerUtil.logError("댓글 목록 조회 실패: {}", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("REPLIES_RETRIEVAL_FAILED", "댓글 목록 조회 중 오류가 발생했습니다"));
        }
    }

    /**
     * 특정 댓글을 업데이트
     *
     * @Param sid 댓글이 달릴 일정의 ID
     * @Param rid 댓글의 ID
     * @Param replyRequestDto 업데이트할 댓글 요청DTO
     * @return 업데이트된 댓글 응답 DTO와 HTTP 상태 코드 200(ok)
     * */
    @PutMapping("/{sid}/replies/{rid}")
    public ResponseEntity<ApiREsponse<ReplyResponseDto>> updateReply(@PathVariable long sid,
                                                                     @PathVariable long rid,
                                                                     @RequestBody ReplyRequestDto replyRequestDto) {

    }

}
