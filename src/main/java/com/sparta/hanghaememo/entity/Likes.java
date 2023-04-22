package com.sparta.hanghaememo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

@Getter
@Entity
@NoArgsConstructor
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "likes_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    public Likes(Users user, Board board){
        this.user = user;
        this.board = board;
    }

    public Likes(Users user, Comment comment){
        this.user = user;
        this.comment = comment;
    }
}
