package com.web.personalstudy.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.ErrorResponse;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Schedule extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sid;
    private String title;
    private String contents;
//    private String weather;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replies = new ArrayList<>();



    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScheduleAssignees> assignees = new ArrayList<>();


    @Builder
    public Schedule(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    // 연관관계 편의 메서드(유저)

    //댓글 추가 메소드
    public void addReply(Reply reply) {
        if(reply.getSchedule() != this) {
            // 연관관계 설정
            reply = Reply.builder()
                    .comment(reply.getComment())
                    .userName(reply.getOwner().getUserName()) // 유저 엔티티 만들때
                    .build();
        }
    }


}
