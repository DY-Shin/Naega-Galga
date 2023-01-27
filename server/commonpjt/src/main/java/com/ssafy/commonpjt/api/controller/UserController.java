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

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    // Postman 사용 시 @RequestBody 를 제거해야 form-data 로 확인 가능
    public void join(@Validated @RequestBody UserDTO userDto) throws Exception {
        userService.join(userDto);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@Validated @RequestBody UserLoginDTO login) throws Exception {
        TokenDTO token = userService.login(login);
        return ResponseEntity.ok(token);
    }

    @PostMapping("logout")
//    @PreAuthorize("hasAnyRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public void logout(@Validated @RequestBody UserLogoutDTO logout) throws Exception {
        userService.logout(logout);
    }

    @DeleteMapping("delete")
//    @PreAuthorize("hasAnyRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@Validated @RequestBody UserLogoutDTO logout) throws Exception {
        userService.delete(logout);
    }

    @GetMapping("")
//    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<?> getMyUserInfo(HttpServletResponse response) throws Exception {
        return ResponseEntity.ok(userService.getMyInfo());
    }

    @GetMapping("{userId}")
    public ResponseEntity<?> getInfo(@Validated @PathVariable("userId") String userId) throws Exception {
        return ResponseEntity.ok(userService.getInfo(userId));
    }

    @PutMapping("")
    @ResponseStatus(HttpStatus.OK)
    public void updateInfo(@Validated @RequestBody UserUpdateDTO userUpdateDto) throws Exception {
        userService.update(userUpdateDto);
    }

    @PutMapping("password")
    @ResponseStatus(HttpStatus.OK)
    public void updatePassword(@Validated @RequestBody UpdatePasswordDTO updatePasswordDto) throws Exception{
        userService.updatePassword(updatePasswordDto);
    }
}
