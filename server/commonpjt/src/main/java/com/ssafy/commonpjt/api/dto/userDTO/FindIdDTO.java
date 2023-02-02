package com.ssafy.commonpjt.api.dto.userDTO;

import com.ssafy.commonpjt.db.entity.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FindIdDTO {
    private String userId;
    private String userName;

    @Builder
    public FindIdDTO(User user) {
        this.userId = user.getUserId();
    }
}
