package com.web.personalstudy.dto.user;

import com.web.personalstudy.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
public class UserTokenResponseDto { //토큰이 필요하다는걸 어떻게 알수 있을까/

    private Long uid;
    private String userName;
    private String email;
    private String password;
    private String token;
    private String createDate;
    private String updateDate;

    public static UserTokenResponseDto from(User user, String token) {
        return new UserTokenResponseDto(
                user.getUid(),
                user.getUserName(),
                user.getEmail(),
                user.getPassword(),
                token,
                user.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분")),
                user.getUpdateDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분"))
        );
    }


}
