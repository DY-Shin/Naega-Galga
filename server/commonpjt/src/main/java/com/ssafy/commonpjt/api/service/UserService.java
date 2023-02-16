package com.ssafy.commonpjt.api.service;

import com.ssafy.commonpjt.api.dto.userDTO.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    void join(UserDTO userDto) throws Exception;
    TokenDTO login(UserLoginDTO login) throws Exception;
    void logout(UserLogoutDTO logout) throws Exception;
    void update(UserUpdateDTO userUpdateDto) throws Exception;
    boolean checkPassword(UpdatePasswordDTO updatePasswordDTO) throws Exception;
    void updatePassword(UpdatePasswordDTO updatePasswordDto) throws Exception;
    UserInfoDTO getInfo(String userId) throws Exception;
    UserInfoDTO getMyInfo() throws Exception;
    List<?> getMyReserve() throws Exception;
    void delete(UserLogoutDTO logout) throws Exception;
    List<?> getMyProductList() throws Exception;
    List<?> getUserProductList(String userId) throws Exception;
    List<?> findMyUserId(String name);
    void findMyPassword(FindPasswordDTO findPasswordDTO) throws Exception;
    TokenDTO reissue(TokenDTO tokenDTO) throws Exception;
}
