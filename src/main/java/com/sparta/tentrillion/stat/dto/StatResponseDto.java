package com.sparta.tentrillion.stat.dto;

import com.sparta.tentrillion.stat.entity.Stat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatResponseDto {
    private Long statId;
    private String message;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String title;

    public StatResponseDto(Stat stat) {
        this.statId = stat.getId();
        this.createdAt = stat.getCreatedAt();
        this.modifiedAt = stat.getModifiedAt();
        this.title = stat.getTitle();
    }
}
