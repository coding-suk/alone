package com.web.personalstudy.service;

import com.web.personalstudy.dto.ReplyRequestDto;
import com.web.personalstudy.dto.ReplyResponseDto;
import com.web.personalstudy.entity.Reply;
import com.web.personalstudy.entity.Schedule;
import com.web.personalstudy.repository.ReplyRepository;
import com.web.personalstudy.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReplyService implements ReplyServiceImpl{

    private final ScheduleRepository scheduleRepository;
    private final ReplyRepository replyRepository;

    /**
     * 댓글을 추가하는 메서드
     * 일정 ID(sid)와 댓글 요청DTO(replyRequestDto)를 받아서 댓글을 생성하고 저장합니다.
     * @Param sid 일정 ID
     * @Param replyRequestDto 댓글 요청 DTO
     * @return 생성된 댓글의 응답DTO
     * */
    @Override
    @Transactional
    public ReplyResponseDto addReply(Long sid, ReplyRequestDto replyRequestDto) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(sid);
        Reply reply = Reply.builder()
                .comment(replyRequestDto.getComment())
                .schedule(schedule)
                .build();
        schedule.addReply(reply);
        replyRepository.save(reply);
        return ReplyResponseDto.from(reply);
    }

    /**
     * 댓글 ID로 특정 댓글을 조회하는 메서드
     * @Param rid 댓글 ㅑㅇ
     * @return 댓글 응답 DTO(Optional)
     * */
    @Override
    @Transactional(readOnly = true)
    public ReplyResponseDto getReply(Long rid) {
        Reply reply = replyRepository.findByIdOrElseThrow(rid);
        return ReplyResponseDto.from(reply);
    }

}
