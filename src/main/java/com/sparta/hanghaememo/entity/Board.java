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
    @Column(name = "board_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    public Board(String title, String contents, Users user) {
        this.title = title;
        this.contents = contents;
        this.user = user;
    }

    public Board(BoardDTO boardDTO){
        this.title = boardDTO.getTitle();
        this.contents = boardDTO.getContents();
    }

    public void update(BoardDTO boardDTO) {
        this.title = boardDTO.getTitle();
        this.contents = boardDTO.getContents();
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
