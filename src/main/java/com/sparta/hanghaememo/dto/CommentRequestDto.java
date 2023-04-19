package com.sparta.hanghaememo.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private Long board_id;
    private String contents;
}
