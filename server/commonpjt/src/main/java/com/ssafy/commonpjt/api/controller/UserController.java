package com.ssafy.commonpjt.api.controller;

import com.ssafy.commonpjt.api.dto.ResponseDTO;
import com.ssafy.commonpjt.api.dto.UserDTO;
import com.ssafy.commonpjt.api.dto.UserLoginRequestDTO;
import com.ssafy.commonpjt.api.dto.UserLogoutRequestDTO;
import com.ssafy.commonpjt.api.service.UserService;
import com.ssafy.commonpjt.common.jwt.JwtTokenProvider;
import com.ssafy.commonpjt.common.lib.Helper;
import com.ssafy.commonpjt.db.entity.User;
import com.ssafy.commonpjt.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseDTO response;

    @PostMapping("")
    // Postman 사용 시 @RequestBody 를 제거해야 form-data 로 확인 가능
    public ResponseEntity<?> join(@Validated @RequestBody UserDTO userDTO, Errors errors) throws Exception{
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return userService.join(userDTO);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@Validated @RequestBody UserLoginRequestDTO login, Errors errors) {
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return userService.login(login);
    }

    @PostMapping("logout")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<?> logout(@Validated @RequestBody UserLogoutRequestDTO logout, Errors errors) {
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return userService.logout(logout);
    }

    @DeleteMapping("delete")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<?> delete(@Validated @RequestBody UserLogoutRequestDTO logout, Errors errors) {
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        userService.delete(logout);
        userService.logout(logout);
        return new ResponseEntity<>("회원 탈퇴되었습니다.", HttpStatus.OK);
    }

    @GetMapping("me")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<User> getMyUserInfo() {
        return ResponseEntity.ok(userService.getMyUser().get());
    }
}
