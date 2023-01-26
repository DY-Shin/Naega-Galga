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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ResponseDTO response;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate redisTemplate;

    public ResponseEntity<?> join(UserDTO userDTO) throws Exception{
        if (userRepository.existsByUserId(userDTO.getUserId())){
            return response.fail("이미 회원가입된 아이디입니다.", HttpStatus.BAD_REQUEST);
        }
        User user = User.builder()
                .userId(userDTO.getUserId())
                .userPassword(passwordEncoder.encode(userDTO.getUserPassword()))
                .userPhone(userDTO.getUserPhone())
                .userName(userDTO.getUserName())
                .userCorporationRegistrationNumber(userDTO.getUserCorporationRegistrationNumber())
                .userAddress(userDTO.getUserAddress())
                .roles(Collections.singletonList(Authority.USER.name()))
                .build();
        userRepository.save(user);
        return response.success("회원가입에 성공했습니다.");
    }

    public ResponseEntity<?> login(UserLoginRequestDTO login) {
        if (userRepository.findByUserId(login.getUserId()).orElse(null) == null) {
            return response.fail("해당하는 유저가 존재하지 않습니다", HttpStatus.BAD_REQUEST);
        }
        UsernamePasswordAuthenticationToken authenticationToken = login.toAuthentication();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenDTO tokenDTO = jwtTokenProvider.generateToken(authentication);
        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), tokenDTO.getRefreshToken(),
                        tokenDTO.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);
        return response.success(tokenDTO, "로그인에 성공했습니다.", HttpStatus.OK);
    }

    public ResponseEntity<?> logout(UserLogoutRequestDTO logout) {
        if (!jwtTokenProvider.validateToken(logout.getAccessToken())) {
            return response.fail("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
        }
        Authentication authentication = jwtTokenProvider.getAuthentication(logout.getAccessToken());
        if (redisTemplate.opsForValue().get("RT:" + authentication.getName()) != null) {
            redisTemplate.delete("RT:" + authentication.getName());
        }
        Long expiration = jwtTokenProvider.getExpiration(logout.getAccessToken());
        redisTemplate.opsForValue()
                .set(logout.getAccessToken(), "logout", expiration, TimeUnit.MILLISECONDS);
        return response.success("로그아웃 되었습니다.");
    }

    @Transactional(readOnly = true)
    public Optional<User> getUser(String userId) {
        return userRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public Optional<User> getMyUser() {
        return SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUserId);
    }

    @Transactional
    public ResponseEntity<?> delete(UserLogoutRequestDTO logout) {
        if (!jwtTokenProvider.validateToken(logout.getAccessToken())) {
            return response.fail("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
        }
        Authentication authentication = jwtTokenProvider.getAuthentication(logout.getAccessToken());
        String userId = authentication.getName();
        userRepository.foreignKeyDelete();
        userRepository.delete(userId);
        userRepository.foreignKeyCheck();
        return response.success();
    }
}

