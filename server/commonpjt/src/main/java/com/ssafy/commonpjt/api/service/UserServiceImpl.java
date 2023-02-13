package com.ssafy.commonpjt.api.service;

import com.ssafy.commonpjt.api.dto.productDTO.ProductInfoDTO;
import com.ssafy.commonpjt.api.dto.reserveDTO.ReserveResponseDTO;
import com.ssafy.commonpjt.api.dto.userDTO.*;
import com.ssafy.commonpjt.common.enums.Authority;
import com.ssafy.commonpjt.common.exception.CustomException;
import com.ssafy.commonpjt.common.exception.ErrorCode;
import com.ssafy.commonpjt.common.jwt.JwtTokenProvider;
import com.ssafy.commonpjt.common.security.SecurityUtil;
import com.ssafy.commonpjt.db.entity.Explanation;
import com.ssafy.commonpjt.db.entity.Meeting;
import com.ssafy.commonpjt.db.entity.Product;
import com.ssafy.commonpjt.db.entity.User;
import com.ssafy.commonpjt.db.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    private final ProductRepository productRepository;
    private final MeetingRepository meetingRepository;
    private final ExplanationRepository explanationRepository;

    // 회원가입 서비스
    @Override
    public void join(UserDTO userDto) throws Exception {
        // 회원 중복 확인
        if (userRepository.existsByUserId(userDto.getUserId())){
            throw new CustomException(ErrorCode.DUPLICATED_VALUE);
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
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
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
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
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
        User user = userRepository.findByUserId(SecurityUtil.getLoginUsername()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        if (userUpdateDto.getUserPhone() != null) user.setUserPhone(userUpdateDto.getUserPhone());
        if (userUpdateDto.getUserName() != null) user.setName(userUpdateDto.getUserName());
        if (userUpdateDto.getCorporateRegistrationNumber() != null) user.setCorporateRegistrationNumber(userUpdateDto.getCorporateRegistrationNumber());
        if (userUpdateDto.getUserAddress() != null) user.setUserAddress(userUpdateDto.getUserAddress());
        userRepository.save(user);
    }

    // 비밀번호 확인
    @Override
    public boolean checkPassword(UpdatePasswordDTO updatePasswordDTO) throws Exception {
        User user = userRepository.findByUserId(SecurityUtil.getLoginUsername()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return user.matchPassword(passwordEncoder, updatePasswordDTO.getCheckPassword());
    }

    // 비밀번호 변경 서비스
    @Override
    public void updatePassword(UpdatePasswordDTO updatePasswordDto) throws Exception {
        User user = userRepository.findByUserId(SecurityUtil.getLoginUsername()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        user.updatePassword(passwordEncoder, updatePasswordDto.getToBePassword());
        userRepository.save(user);
    }

    // 유저 정보 검색
    @Override
    @Transactional
    public UserInfoDTO getInfo(String userId) throws Exception {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return new UserInfoDTO(user);
    }

    // 내 정보 검색
    @Override
    @Transactional
    public UserInfoDTO getMyInfo() throws Exception {
        User user = userRepository.findByUserId(SecurityUtil.getLoginUsername()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return new UserInfoDTO(user);
    }

    // 회원 탈퇴
    @Override
    public void delete(UserLogoutDTO logout) throws Exception {
        if (!jwtTokenProvider.validateToken(logout.getAccessToken())) {
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }
        Authentication authentication = jwtTokenProvider.getAuthentication(logout.getAccessToken());
        String userId = authentication.getName();
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        if (!user.matchPassword(passwordEncoder, logout.getCheckPassword())){
            throw new CustomException(ErrorCode.NOT_MY_CONTENTS);
        }
        userRepository.delete(user);
        logout(logout);
    }

    // 내가 등록한 매물 목록 조회
    @Override
    @Transactional
    public List<?> getMyProductList() throws Exception {
        User user = userRepository.findByUserId(SecurityUtil.getLoginUsername()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return productRepository.findAllByProductSeller(user);
    }

    // 다른 유저가 등록한 매물 목록 조회
    @Override
    @Transactional
    public List<?> getUserProductList(String userId) throws Exception {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return productRepository.findAllByProductSeller(user);
    }

    // 예약 목록 조회
    @Override
    @Transactional
    public List<?> getMyReserve() throws Exception {
        User user = userRepository.findByUserId(SecurityUtil.getLoginUsername()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        List<Meeting> ownerMeeting = meetingRepository.findAllByOwner(user);
        List<Meeting> guestMeeting = meetingRepository.findAllByGuest(user);
        List<Explanation> guestExplanation = explanationRepository.findAllByReserveUser(user);
        List<Integer> meetingIndexList = new ArrayList<>();
        List<ReserveResponseDTO> meetingList = new ArrayList<>();
        for (Meeting meeting : ownerMeeting) {
            meetingIndexList.add(meeting.getMeetingIndex());
        }
        for (Meeting meeting : guestMeeting) {
            meetingIndexList.add(meeting.getMeetingIndex());
        }
        for (Explanation explanation : guestExplanation) {
            meetingIndexList.add(explanation.getMeeting().getMeetingIndex());
        }
        for (int index : meetingIndexList) {
            Meeting meeting = meetingRepository.findByMeetingIndex(index).orElseThrow(() -> new CustomException(ErrorCode.NO_CONTENT));
            Product product = meeting.getProduct();
            if (product != null) {
                List<Explanation> explanation = explanationRepository.findAllByMeeting(meeting);
                ProductInfoDTO productInfo = new ProductInfoDTO(product);
                // 설명회
                ReserveResponseDTO reserve;
                if (meeting.getOwner().getUserIndex() == user.getUserIndex()){
                    // 설명회 판매자
                    // explanation table 접근
                    reserve = ReserveResponseDTO.builder()
                            .meetingIndex(meeting.getMeetingIndex())
                            .type("Explanation")
                            .role("Owner")
                            .meetingUrl(meeting.getMeetingUrl())
                            .reserveAt(meeting.getReserveAt())
                            .guestNumber(explanation.size())
                            .product(productInfo)
                            .build();
                } else {
                    // 설명회 구매자
                    // explanation table 에 접근
                    reserve = ReserveResponseDTO.builder()
                            .meetingIndex(meeting.getMeetingIndex())
                            .type("Explanation")
                            .role("Guest")
                            .meetingUrl(meeting.getMeetingUrl())
                            .reserveAt(meeting.getReserveAt())
                            .owner(new OwnerDTO(meeting.getOwner()))
                            .product(productInfo)
                            .build();
                }
                meetingList.add(reserve);
            } else {
                ReserveResponseDTO reserve;
                if (meeting.getOwner().getUserIndex() == user.getUserIndex()) {
                    reserve = ReserveResponseDTO.builder()
                            .meetingIndex(meeting.getMeetingIndex())
                            .type("Meeting")
                            .role("Owner")
                            .meetingUrl(meeting.getMeetingUrl())
                            .reserveAt(meeting.getReserveAt())
                            .guest(new GuestDTO(meeting.getGuest()))
                            .build();
                } else {
                    reserve = ReserveResponseDTO.builder()
                            .meetingIndex(meeting.getMeetingIndex())
                            .type("Meeting")
                            .role("Guest")
                            .meetingUrl(meeting.getMeetingUrl())
                            .reserveAt(meeting.getReserveAt())
                            .owner(new OwnerDTO(meeting.getOwner()))
                            .build();
                }
                meetingList.add(reserve);
            }
        }
        return meetingList;
    }

    // 아이디 찾기
    @Override
    public List<?> findMyUserId(String name) {
        List<User> userList = userRepository.findAllByName(name);
        List<String> userIdList = new ArrayList<>();
        for (User user : userList) {
            userIdList.add(user.getUserId());
        }
        return userIdList;
    }

    // 비밀번호 재설정 (암호화로 인해 복호화가 불가능) -> true 를 반환받으면 updatePassword 메소드 실행
    @Override
    public void findMyPassword(FindPasswordDTO findPasswordDTO) throws Exception {
        User user = userRepository.findByUserId(findPasswordDTO.getUserId()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        if (!user.getName().equals(findPasswordDTO.getUserName())){
            throw new CustomException(ErrorCode.NOT_MY_CONTENTS);
        }
        if (!user.getUserPhone().equals(findPasswordDTO.getUserPhone())){
            throw new CustomException(ErrorCode.NOT_MY_CONTENTS);
        }
        user.updatePassword(passwordEncoder, findPasswordDTO.getToBePassword());
        userRepository.save(user);
    }

    // 토큰 만료시 토큰 재발급
    @Override
    public TokenDTO reissue(TokenDTO tokenDTO) throws Exception {
        if(!jwtTokenProvider.validateToken(tokenDTO.getRefreshToken())) {
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }
        Authentication authentication = jwtTokenProvider.getAuthentication(tokenDTO.getAccessToken());
        String refreshToken = (String) redisTemplate.opsForValue().get("RT:" + authentication.getName());
        if (!refreshToken.equals(tokenDTO.getRefreshToken())) {
            throw new CustomException(ErrorCode.NO_CONTENT);
        }
        TokenDTO token = jwtTokenProvider.generateToken(authentication);
        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), token.getRefreshToken(),
                        token.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);
        return token;
    }
}
