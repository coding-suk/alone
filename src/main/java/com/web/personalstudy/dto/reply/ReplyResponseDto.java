package com.web.personalstudy.dto.reply;

import com.web.personalstudy.entity.Reply;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
public class ReplyResponseDto {

    private Long rid;
    private String comment;
    private String createDate;
    private String updateDate;

    public static ReplyResponseDto from(Reply reply) { // from 메서드  static factory 메소드, 정적 메서드
        return new ReplyResponseDto(
                reply.getRid(),
                reply.getComment(),
                reply.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분")),
                reply.getUpdateDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분"))
        );
    }

    public ReplyResponseDto(Reply reply) { // 생성자
        this.rid = reply.getRid();
        this.comment = reply.getComment();
        this.createDate = reply.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분"));
        this.updateDate = reply.getUpdateDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분"));
    }

}
