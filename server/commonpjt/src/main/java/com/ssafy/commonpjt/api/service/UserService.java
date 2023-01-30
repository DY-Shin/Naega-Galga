package com.ssafy.commonpjt.api.service;

import com.ssafy.commonpjt.api.dto.user.*;

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
    void delete(UserLogoutDTO logout) throws Exception;
    List<?> wishList(String userId) throws Exception;
    List<?> getMyProductList() throws Exception;
    List<?> getUserProductList(String userId) throws Exception;
}
