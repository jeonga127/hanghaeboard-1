package com.sparta.hanghaememo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.hanghaememo.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false)
    private String contents;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    public Comment(Users user, Board board, CommentRequestDto commentRequestDto){
        this.user = user;
        this.board = board;
        this.contents =commentRequestDto.getContents();
    }

    public void update(CommentRequestDto commentRequestDto){
        this.contents=commentRequestDto.getContents();
    }

}
