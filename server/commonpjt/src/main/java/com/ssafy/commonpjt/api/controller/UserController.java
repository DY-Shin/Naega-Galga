package com.ssafy.commonpjt.api.controller;

import com.ssafy.commonpjt.api.dto.user.*;
import com.ssafy.commonpjt.api.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;



@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    // 회원가입
    // Postman 사용 시 @RequestBody 를 제거해야 form-data 로 확인 가능
    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public void join(@Validated @RequestBody UserDTO userDto) throws Exception {
        userService.join(userDto);
    }

    // 로그인
    @PostMapping("login")
    public ResponseEntity<?> login(@Validated @RequestBody UserLoginDTO login) throws Exception {
        TokenDTO token = userService.login(login);
        return ResponseEntity.ok(token);
    }

    // 로그아웃
    @PostMapping("logout")
//    @PreAuthorize("hasAnyRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public void logout(@Validated @RequestBody UserLogoutDTO logout) throws Exception {
        userService.logout(logout);
    }

    // 회원 탈퇴
    @DeleteMapping("delete")
//    @PreAuthorize("hasAnyRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@Validated @RequestBody UserLogoutDTO logout) throws Exception {
        userService.delete(logout);
    }

    // 내 정보 조회
    @GetMapping("")
//    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<?> getMyUserInfo(HttpServletResponse response) throws Exception {
        return ResponseEntity.ok(userService.getMyInfo());
    }

    // 아이디로 다른 회원 정보 조회
    @GetMapping("{userId}")
    public ResponseEntity<?> getInfo(@Validated @PathVariable("userId") String userId) throws Exception {
        return ResponseEntity.ok(userService.getInfo(userId));
    }

    // 내 정보 수정
    @PutMapping("")
    @ResponseStatus(HttpStatus.OK)
    public void updateInfo(@Validated @RequestBody UserUpdateDTO userUpdateDto) throws Exception {
        userService.update(userUpdateDto);
    }

    // 내 비밀번호 수정
    @PutMapping("password")
    @ResponseStatus(HttpStatus.OK)
    public void updatePassword(@Validated @RequestBody UpdatePasswordDTO updatePasswordDto) throws Exception{
        userService.updatePassword(updatePasswordDto);
    }

    // 비밀번호 체크
    @GetMapping("password")
    public boolean checkPassword(@Validated @RequestBody UpdatePasswordDTO updatePasswordDTO) throws Exception {
        return userService.checkPassword(updatePasswordDTO);
    }
}
