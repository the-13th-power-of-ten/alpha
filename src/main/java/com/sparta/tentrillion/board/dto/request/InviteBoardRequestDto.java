package com.sparta.tentrillion.board.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InviteBoardRequestDto {

    @NotNull(message = "Enter the user's ID to invite.")
    private String username;

}
