package com.web.personalstudy.service;

import com.web.personalstudy.dto.reply.ReplyRequestDto;
import com.web.personalstudy.dto.reply.ReplyResponseDto;
import com.web.personalstudy.entity.Reply;
import com.web.personalstudy.entity.Schedule;
import com.web.personalstudy.repository.ReplyRepository;
import com.web.personalstudy.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
     * @Param rid 댓글 ID
     * @return 댓글 응답 DTO(Optional)
     * */
    @Override
    @Transactional(readOnly = true)
    public ReplyResponseDto getReply(Long rid) {
        Reply reply = replyRepository.findByIdOrElseThrow(rid);
        return ReplyResponseDto.from(reply);
    }

    /**
     * 일정 ID로 해당 일정에 달린 모든 댓글을 조회하는 메서드
     * @Param sid 일정 ID
     * @return 댓글 응답 DTO 리스트
     * */
    @Override
    public List<ReplyResponseDto> getReplies(Long sid) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(sid);
        return schedule.getReplies().stream()
                .map(ReplyResponseDto::from)
                .collect(Collectors.toList());
    }

    /**
     * 댓글을 수정하는 메서드
     * 댓글 ID와 댓글 요청 DTo를 받아서 댓글을 수정하고 저장
     * @Param rid 댓글 ID
     * @Param replyRequestDto 댓글 요청 DTO
     * @return 수정된 댓글의 응답 DTO
     * */
    @Override
    public ReplyResponseDto updateReply(Long rid, ReplyRequestDto replyRequestDto) {
        Reply reply = replyRepository.findByIdOrElseThrow(rid);
        reply.replyUpdate(replyRequestDto);
        return ReplyResponseDto.from(replyRepository.save(reply));
    }

    /**
     * 댓글을 삭제하는 메서드
     * 댓글 ID로 해당 댓글을 조회하고 삭제
     * @Param rid 댓글 ID
     * @Param replyRequestDto 댓글 요청 DTO
     * @return 수정된 댓글의 응답 DTO
     * */
    @Override
    public void deleteReply(Long rid) {
        Reply reply = replyRepository.findByIdOrElseThrow(rid);
        replyRepository.delete(reply);
    }





}
