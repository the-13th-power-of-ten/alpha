package com.sparta.tentrillion.board.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardRequestDto {

    @NotBlank(message = "Enter the title of the board.")
    private String title;

    @NotBlank(message = "Enter the description of the board.")
    private String description;
}
