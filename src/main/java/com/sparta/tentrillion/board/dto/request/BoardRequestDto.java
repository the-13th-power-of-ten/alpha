package com.sparta.tentrillion.board.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardRequestDto {

    @NotNull(message = "Enter the title of the board.")
    private String title;

    @NotNull(message = "Enter the description of the board.")
    private String description;
}
