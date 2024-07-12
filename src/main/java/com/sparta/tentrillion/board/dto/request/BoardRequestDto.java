package com.sparta.tentrillion.board.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardRequestDto {

    @NotNull(message = "보드 제목을 입력해주세요.")
    private String title;

    @NotNull(message = "보드 설명을 입력해주세요.")
    private String description;
}
