package com.ssafy.commonpjt.controller;

import com.ssafy.commonpjt.dto.*;
import com.ssafy.commonpjt.jwt.JwtTokenProvider;
import com.ssafy.commonpjt.lib.Helper;
import com.ssafy.commonpjt.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseDto response;

    @PostMapping("")
    // Postman 사용 시 @RequestBody 를 제거해야 form-data 로 확인 가능
    public ResponseEntity<?> join(@Validated @RequestBody UserDto userDto, Errors errors) throws Exception{
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return userService.join(userDto);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@Validated @RequestBody UserLoginRequestDto login, Errors errors) {
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return userService.login(login);
    }

    @PostMapping("logout")
    public ResponseEntity<?> logout(@Validated @RequestBody UserLogoutRequestDto logout, Errors errors) {
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return userService.logout(logout);
    }
}
