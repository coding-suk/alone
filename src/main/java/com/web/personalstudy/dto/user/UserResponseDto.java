package com.web.personalstudy.dto.user;

import com.web.personalstudy.entity.User;
import com.web.personalstudy.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private Long uid;
    private String userName;
    private String email;
    private UserRole userRole;
    private String createdDate;
    private String updatedDate;

    public static UserResponseDto from(User user) {
        return new UserResponseDto(
                user.getUid(),
                user.getUserName(),
                user.getEmail(),
                user.getRole(), // 왜 userRole이 아니라 Role인거지??
                user.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분")),
                user.getUpdateDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분"))
        );
    }

}
