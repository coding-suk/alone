package com.web.personalstudy.entity;

import com.web.personalstudy.dto.reply.ReplyRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Reply extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rid;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @Builder
    public Reply(String comment, String userName, User owner, Schedule schedule) {
        this.comment = comment;
        this.owner = owner;
        this.schedule = schedule;
        schedule.getReplies().add(this);
    }

    public void replyUpdate(ReplyRequestDto replyRequestDto) {
        this.comment = replyRequestDto.getComment();
    }


}
