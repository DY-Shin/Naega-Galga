package com.ssafy.commonpjt.api.service;

import com.ssafy.commonpjt.api.dto.user.*;
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
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate redisTemplate;

    // 회원가입 서비스
    @Override
    public void join(UserDTO userDto) throws Exception {
        // 회원 중복 확인
        if (userRepository.existsByUserId(userDto.getUserId())){
            throw new Exception("Already Exists Id");
        }
        // DB에 저장
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

    // 로그인 서비스
    @Override
    public TokenDTO login(UserLoginDTO login) throws Exception {
        // 회원 정보 조회
        if (userRepository.findByUserId(login.getUserId()).orElse(null) == null) {
            throw new Exception("No User Exists");
        }
        // 로그인시 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken = login.toAuthentication();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenDTO tokenDto = jwtTokenProvider.generateToken(authentication);
        // 토큰 정보 설정
        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), tokenDto.getRefreshToken(),
                        tokenDto.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);
        return tokenDto;
    }

    // 로그아웃 서비스
    @Override
    public void logout(UserLogoutDTO logout) throws Exception {
        // 토큰 유효성 검사
        if (!jwtTokenProvider.validateToken(logout.getAccessToken())) {
            throw new Exception("Invalid Request");
        }
        // 토큰이 유효하다면 Access 토큰을 받아옴
        Authentication authentication = jwtTokenProvider.getAuthentication(logout.getAccessToken());
        if (redisTemplate.opsForValue().get("RT:" + authentication.getName()) != null) {
            redisTemplate.delete("RT:" + authentication.getName());
        }
        // 토큰을 즉시 만료시키고 블랙리스트에 등록
        Long expiration = jwtTokenProvider.getExpiration(logout.getAccessToken());
        redisTemplate.opsForValue()
                .set(logout.getAccessToken(), "logout", expiration, TimeUnit.MILLISECONDS);
    }

    // 회원정보 수정 서비스
    @Override
    public void update(UserUpdateDTO userUpdateDto) throws Exception {
        User user = userRepository.findByUserId(SecurityUtil.getLoginUsername()).orElseThrow(() -> new Exception("No User Exists"));
        if (userUpdateDto.getUserId() != null) user.setUserId(userUpdateDto.getUserId());
        if (userUpdateDto.getUserPhone() != null) user.setUserPhone(userUpdateDto.getUserPhone());
        if (userUpdateDto.getUserName() != null) user.setName(userUpdateDto.getUserName());
        if (userUpdateDto.getCorporateRegistrationNumber() != null) user.setCorporateRegistrationNumber(userUpdateDto.getCorporateRegistrationNumber());
        if (userUpdateDto.getUserAddress() != null) user.setUserAddress(userUpdateDto.getUserAddress());
        userRepository.save(user);
    }

    // 비밀번호 확인
    @Override
    public boolean checkPassword(UpdatePasswordDTO updatePasswordDTO) throws Exception {
        User user = userRepository.findByUserId(SecurityUtil.getLoginUsername()).orElseThrow(() -> new Exception("No User Exists"));
        return user.matchPassword(passwordEncoder, updatePasswordDTO.getCheckPassword());
    }

    // 비밀번호 변경 서비스
    @Override
    public void updatePassword(UpdatePasswordDTO updatePasswordDto) throws Exception {
        User user = userRepository.findByUserId(SecurityUtil.getLoginUsername()).orElseThrow(() -> new Exception("No User Exists"));
//        if (!user.matchPassword(passwordEncoder, updatePasswordDto.getCheckPassword())) {
//            throw new Exception("Incorrect Password");
//        }
        user.updatePassword(passwordEncoder, updatePasswordDto.getToBePassword());
        userRepository.save(user);
    }

    // 유저 정보 검색
    @Override
    public UserInfoDTO getInfo(String userId) throws Exception {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new Exception("No User Exists"));
        return new UserInfoDTO(user);
    }

    // 내 정보 검색
    @Override
    public UserInfoDTO getMyInfo() throws Exception {
        User user = userRepository.findByUserId(SecurityUtil.getLoginUsername()).orElseThrow(() -> new Exception("No User Exists"));
        return new UserInfoDTO(user);
    }

    // 회원 탈퇴
    @Override
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
        userRepository.delete(user);
        logout(logout);
    }
}

