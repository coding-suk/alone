package com.web.personalstudy.service;

import com.web.personalstudy.dto.user.UserRequestDto;
import com.web.personalstudy.dto.user.UserResponseDto;
import com.web.personalstudy.dto.user.UserTokenResponseDto;


import java.util.List;

public interface UserServiceImpl {

    UserTokenResponseDto createUser(UserRequestDto userRequestDto);

    UserResponseDto getUser(Long uid);

    List<UserResponseDto> getUsers();

    UserResponseDto updateUser(Long uid, UserRequestDto userRequestDto);

    void deleteUser(Long uid);

}
