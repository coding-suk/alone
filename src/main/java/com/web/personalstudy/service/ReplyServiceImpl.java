package com.web.personalstudy.service;


import com.web.personalstudy.dto.ReplyRequestDto;
import com.web.personalstudy.dto.ReplyResponseDto;

import java.util.List;

public interface ReplyServiceImpl {
    ReplyResponseDto addReply(Long sid, ReplyRequestDto replyRequestDto);

    ReplyResponseDto getReply(Long rid);

    List<ReplyResponseDto> getReplies(Long sid);

    ReplyResponseDto updateReply(Long rid, ReplyRequestDto replyRequestDto);

    void deleteReply(Long rid);
}
