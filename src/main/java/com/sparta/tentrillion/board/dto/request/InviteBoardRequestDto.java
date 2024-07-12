package com.sparta.tentrillion.board.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InviteBoardRequestDto {

    @Email
    @NotNull(message = "초대할 사용자의 이메일을 입력해주세요")
    private String username;

}
