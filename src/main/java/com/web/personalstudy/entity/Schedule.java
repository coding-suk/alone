package com.web.personalstudy.entity;

import com.web.personalstudy.dto.schedule.ScheduleRequestDto;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScheduleAssignees> assignees = new ArrayList<>();

    // 이 메소드는 왜 필요한거지??
    public void updateSchedule(ScheduleRequestDto scheduleRequestDto) {
        this.title = scheduleRequestDto.getTitle();
        this.contents = scheduleRequestDto.getContents();
    }

    //소유자 업데이트 메소드
    public void updateOwner(User newOwner) {
        this.owner = newOwner;
    }


    @Builder
    public Schedule(String title, String contents, User owner) {
        this.title = title;
        this.contents = contents;
        this.owner = owner;
    }

    // 연관관계 편의 메서드(유저)
    public void addAssignedUser(User user) {
        ScheduleAssignees assignee = new ScheduleAssignees(user, this); // this?
        this.assignees.add(assignee);
        if(!user.getScheduleAssignees().contains(assignee)) {
            user.addAssignedSchedule(assignee);
        }
    }

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
