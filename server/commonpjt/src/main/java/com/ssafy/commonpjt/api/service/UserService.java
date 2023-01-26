package com.ssafy.commonpjt.api.service;

import com.ssafy.commonpjt.api.dto.*;
import com.ssafy.commonpjt.common.enums.Authority;
import com.ssafy.commonpjt.common.jwt.JwtTokenProvider;
import com.ssafy.commonpjt.common.security.SecurityUtil;
import com.ssafy.commonpjt.db.entity.User;
import com.ssafy.commonpjt.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate redisTemplate;

    public void join(UserDTO userDto) throws Exception {
        if (userRepository.existsByUserId(userDto.getUserId())){
            throw new Exception("Already Exists Id");
        }
        User user = User.builder()
                .userId(userDto.getUserId())
                .userPassword(passwordEncoder.encode(userDto.getUserPassword()))
                .userPhone(userDto.getUserPhone())
                .name(userDto.getUserName())
                .corporateRegistrationNumber(userDto.getCorporateRegistrationNumber())
                .userAddress(userDto.getUserAddress())
                .roles(Collections.singletonList(Authority.USER.name()))
                .build();
        userRepository.save(user);
    }

    public TokenDTO login(UserLoginDTO login) throws Exception {
        System.out.println(login.getUserId());
        System.out.println(userRepository.findByUserId(login.getUserId()));
        if (userRepository.findByUserId(login.getUserId()).orElse(null) == null) {
            throw new Exception("No User Exists");
        }
        UsernamePasswordAuthenticationToken authenticationToken = login.toAuthentication();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenDTO tokenDto = jwtTokenProvider.generateToken(authentication);
        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), tokenDto.getRefreshToken(),
                        tokenDto.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);
        return tokenDto;
    }

    public void logout(UserLogoutDTO logout) throws Exception {
        if (!jwtTokenProvider.validateToken(logout.getAccessToken())) {
            throw new Exception("Invalid Request");
        }
        Authentication authentication = jwtTokenProvider.getAuthentication(logout.getAccessToken());
        if (redisTemplate.opsForValue().get("RT:" + authentication.getName()) != null) {
            redisTemplate.delete("RT:" + authentication.getName());
        }
        Long expiration = jwtTokenProvider.getExpiration(logout.getAccessToken());
        redisTemplate.opsForValue()
                .set(logout.getAccessToken(), "logout", expiration, TimeUnit.MILLISECONDS);
    }

    public void update(UserUpdateDTO userUpdateDto) throws Exception {
        User user = userRepository.findByUserId(SecurityUtil.getLoginUsername()).orElseThrow(() -> new Exception("No User Exists"));
        userUpdateDto.getUserId().ifPresent(user::updateId);
        userUpdateDto.getUserPhone().ifPresent(user::updatePhone);
        userUpdateDto.getUserName().ifPresent(user::updateName);
        userUpdateDto.getCorporateRegistrationNumber().ifPresent(user::updateCorporateRegistrationNumber);
        userUpdateDto.getUserAddress().ifPresent(user::updateAddress);
    }

    public void updatePassword(UpdatePasswordDTO updatePasswordDto) throws Exception {
        User user = userRepository.findByUserId(SecurityUtil.getLoginUsername()).orElseThrow(() -> new Exception("No User Exists"));
        if (!user.matchPassword(passwordEncoder, updatePasswordDto.getCheckPassword())) {
            throw new Exception("Incorrect Password");
        }
        user.updatePassword(passwordEncoder, updatePasswordDto.getToBePassword());
    }

    public UserInfoDTO getInfo(String userId) throws Exception {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new Exception("No User Exists"));
        return new UserInfoDTO(user);
    }

    public UserInfoDTO getMyInfo() throws Exception {
        User user = userRepository.findByUserId(SecurityUtil.getLoginUsername()).orElseThrow(() -> new Exception("No User Exists"));
        return new UserInfoDTO(user);
    }

    // 회원 탈퇴
    public void delete(UserLogoutDTO logout) throws Exception {
        if (!jwtTokenProvider.validateToken(logout.getAccessToken())) {
            throw new Exception("Invalid Request");
        }
        Authentication authentication = jwtTokenProvider.getAuthentication(logout.getAccessToken());
        String userId = authentication.getName();
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new Exception("No User Exists"));
        if (!user.matchPassword(passwordEncoder, logout.getCheckPassword())){
            throw new Exception("Incorrect Password");
        }
        userRepository.foreignKeyDelete();
        userRepository.delete(user);
        userRepository.foreignKeyCheck();
        logout(logout);
    }
}

