package com.sparta.hanghaememo.dto;

import com.sparta.hanghaememo.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class BoardDTO {
    private String username;
    private String password;
    private String title;
    private String contents;

}
