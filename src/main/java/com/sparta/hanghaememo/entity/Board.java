package com.sparta.hanghaememo.entity;

import com.sparta.hanghaememo.dto.BoardDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Board extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public Board(String username, String password, String title, String contents) {
        this.username = username;
        this.password = password;
        this.title = title;
        this.contents = contents;
    }

    public Board(BoardDTO boardDTO){
        this.username = boardDTO.getUsername();
        this.password = boardDTO.getPassword();
        this.title = boardDTO.getTitle();
        this.contents = boardDTO.getContents();
    }


    public void update(BoardDTO boardDTO) {
        this.username = boardDTO.getUsername();
        this.password = boardDTO.getPassword();
        this.title = boardDTO.getTitle();
        this.contents = boardDTO.getContents();
    }

}
