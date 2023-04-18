package com.sparta.hanghaememo.entity;

import com.sparta.hanghaememo.dto.BoardRequestDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Board extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    public Board(String username,String title, String contents, Users user) {
        this.username = username;
        this.title = title;
        this.contents = contents;
        this.user = user;
    }

    public Board(BoardRequestDTO boardDTO){
        this.username = boardDTO.getUsername();
        this.title = boardDTO.getTitle();
        this.contents = boardDTO.getContents();
    }

    public void update(BoardRequestDTO boardDTO) {
        this.username = boardDTO.getUsername();
        this.title = boardDTO.getTitle();
        this.contents = boardDTO.getContents();
    }

    public void addUser(Users user) {
        this.user = user;
    }
}
