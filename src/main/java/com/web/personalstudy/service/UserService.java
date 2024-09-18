package com.web.personalstudy.service;

import com.web.personalstudy.common.util.JwtUtils;
import com.web.personalstudy.common.util.PasswordUtils;
import com.web.personalstudy.dto.user.UserRequestDto;
import com.web.personalstudy.dto.user.UserResponseDto;
import com.web.personalstudy.dto.user.UserTokenResponseDto;
import com.web.personalstudy.entity.User;
import com.web.personalstudy.entity.UserRole;
import com.web.personalstudy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceImpl{

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    /**
     *
     * 새로운 사용자를 생성하고 JWT토큰을 반환함
     *
     * @Param userRequestDto 사용자 요청 DTo
     * @return 생성된 사용자와 JWT토큰을 포함하는 응답 DTO
     * */
    @Transactional
    @Override
    public UserTokenResponseDto createUser(UserRequestDto userRequestDto) {
        if(userRepository.findByEmail(userRequestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("사용중인 이메일 입니다");
        }
        String password = PasswordUtils.hashPassword(userRequestDto.getPassword());
        UserRole role = userRequestDto.getUserRole() != null ? userRequestDto.getUserRole() : UserRole.USER;
        User user = User.builder()
                .userName(userRequestDto.getUserName())
                .email(userRequestDto.getEmail())
                .password(password)
                .role(role)
                .build();
        userRepository.save(user);
        String token = jwtUtils.generateToken(user.getUserName(), user.getRole());
        return UserTokenResponseDto.from(user, token);
    }

    /**
     * ID로 특정 사용자를 조회
     *
     * @Param uid 사용자 ID
     * @return 조회된 사용자 응답 DTO
     *
     * */
    @Override
    public UserResponseDto getUser(Long uid) {
        User user = userRepository.findById(uid)
                .orElseThrow(()-> new NoSuchElementException("유저를 찾을수 없습니다"));
        return UserResponseDto.from(user);
    }

    /**
     *  모든 사용자를 조회
     *
     * @retrun 모든 사용자 응답 DTO 목록
     * */
    @Override
    public List<UserResponseDto> getUsers() {
        return userRepository.findAll().stream()
                .map(UserResponseDto::from)
                .collect(Collectors.toList());
    }

    /**
     * 사용자를 업데이트합니다
     *
     * @Param uid 사용자 ID
     * @Param userRequestDto 사용자 요청 DTO
     * @return 업데이트된 사용자 응답 DTO
     * */
    @Override
    @Transactional
    public UserResponseDto updateUser(Long uid, UserRequestDto userRequestDto) {
        User user = userRepository.findById(uid)
                .orElseThrow(()-> new NoSuchElementException("유저를 찾을 수 없습니다"));
        user.updateUser(userRequestDto);
        return UserResponseDto.from(userRepository.save(user));
    }

    /**
     * 사용자를 삭제합니다
     *
     * @Param uid 사용자 ID
     * */
    @Override
    @Transactional
    public void deleteUser(Long uid) {
        userRepository.findById(uid)
                .orElseThrow(()-> new IllegalArgumentException("유저를 찾을 수 없습니다"));
        userRepository.deleteById(uid);
    }

}
