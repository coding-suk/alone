package com.web.personalstudy.controller;

import com.web.personalstudy.common.response.ApiResponse;
import com.web.personalstudy.common.util.LoggerUtil;
import com.web.personalstudy.dto.user.UserRequestDto;
import com.web.personalstudy.dto.user.UserResponseDto;
import com.web.personalstudy.dto.user.UserTokenResponseDto;
import com.web.personalstudy.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class UserController {

    private final UserService userService;

    /**
     *
     * 사용자 등록을 처리
     *
     * @Param userRepository 사용자 등록 요청 데이터 전송 객체
     * @return 등록된 사용자 정보와 JWT토큰을 포함한 응답
     * */
    @PostMapping("/v1/users")
    public ResponseEntity<ApiResponse<UserTokenResponseDto>> register(@RequestBody UserRequestDto userRequestDto) {
        try{
            UserTokenResponseDto userTokenResponseDto = userService.createUser(userRequestDto);
            return ResponseEntity.ok(ApiResponse.success(userTokenResponseDto));
        } catch (Exception e) {
            LoggerUtil.logError("회원가입 실패", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("USER SIGN ERROR", e.getMessage()));
        }
    }

    /**
     *
     * 주어진 사용자 ID에 대한 사용자 정보를 조회합니다
     *
     * @Param uid 사용자 ID
     * @return 조회된 사용자 정보
     *
     * */
    @GetMapping("/v1/users/{uid}")
    public ResponseEntity<ApiResponse<UserResponseDto>> getUser(@PathVariable long uid) {
        try {
            UserResponseDto userResponse = userService.getUser(uid);
            return ResponseEntity.ok(ApiResponse.success(userResponse));
        } catch (NoSuchElementException e) {
            LoggerUtil.logError("사용자 조회 실패: {}", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("USER_NOT_FOUND", "사용자를 찾을 수 없습니다"));
        } catch (Exception e) {
            LoggerUtil.logError("토큰 조회 실패: {}", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("TOKEN_INVAILD", "유효하지 않은 토큰입니다"));
        }
    }

    /**
     * 모든 사용자 정보를 조회함
     *
     * @return 모든 사용자 정보 목록
     * */
    @GetMapping("/v1/users")
    public ResponseEntity<ApiResponse<List<UserResponseDto>>> getAllUsers() {
        try{
            // 모든 사용자 정보를 조회하고, 응답으로 반환
            List<UserResponseDto> users = userService.getUsers();
            return ResponseEntity.ok(ApiResponse.success(users));
        } catch (Exception e) {
            LoggerUtil.logError("사용자 조회 실패: {}", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("USER_FETCH_ERROR", "사용자 목록을 가져오는데 실패했습니다"));
        }
    }

    /**
     *
     * 사용자 정보를 업데이트함
     *
     * @Param uid 사용자 ID
     * @Param userRequestDto 사용자 업데이트 요청 데이터 전송 객체
     * @return 업데이트된 사용자 정보
     * */
    @PutMapping("/v1/users/{uid}")
    public ResponseEntity<ApiResponse<UserResponseDto>> updateUser(@PathVariable long uid,
                                                                   @RequestBody UserRequestDto userRequestDto) {
        try {
            UserResponseDto updateUser = userService.updateUser(uid, userRequestDto);
            return ResponseEntity.ok(ApiResponse.success(updateUser));
        }catch (Exception e) {
            LoggerUtil.logError("사용자 업데이트 실패: {}", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("USER_UPDATE_ERROR", "사용자 업데이트 실패했습니다"));
        }
    }

    /**
     *
     * 사용자를 삭제
     *
     * @Param uid 사용자 ID
     * @return 요청이 성공적으로 처리됨
     * */
    @DeleteMapping("/v1/users/{uid}")
    public ResponseEntity<Void> deleteUser(@PathVariable long uid) {
        try {
            userService.deleteUser(uid);
            LoggerUtil.logInfo("deleteUser", uid);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            LoggerUtil.logError("사용자 삭제 실패: {}", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
