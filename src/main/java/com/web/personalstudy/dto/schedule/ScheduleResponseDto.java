package com.web.personalstudy.dto.schedule;

import com.web.personalstudy.entity.Reply;
import com.web.personalstudy.entity.Schedule;
import com.web.personalstudy.entity.ScheduleAssignees;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {

    private Long sid;
    private String title;
    private String contents;
    private String createdDate;
    private String updateDate;

    public static ScheduleResponseDto from(Schedule schedule) {
        // Reply리스트에서 comment만 추출
        List<String> comment = schedule.getReplies().stream()
                .map(Reply::getComment) //Reply객체의 comment 필드 추출
                .toList();

        // Assignee 정보 추출
        List<Long> assigneeIds = new ArrayList<>();
        List<String> assigneeNames = new ArrayList<>();
        List<String> assigneeEmails = new ArrayList<>();


        schedule.getAssignees().stream()
                .map(ScheduleAssignees::getUser)
                .distinct()
                .forEach(user -> {
                    assigneeIds.add(user.getUid());
                    assigneeNames.add(user.getUserName());
                    assigneeEmails.add(user.getEmail());
                });

        return new ScheduleResponseDto(
                schedule.getSid(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분")),
                schedule.getUpdateDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분")),
                comment.toString(),
                assigneeIds,
                String.join(", ", assigneeNames), // 여러 담당자 이름을 하나의 문자열로 합침
                String.join(", ", assigneeEmails)); // 여러 담당자 이메일을 하나의 문자열로 합침


    }

}
