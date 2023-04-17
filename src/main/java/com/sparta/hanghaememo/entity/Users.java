package com.sparta.hanghaememo.entity;

import com.sparta.hanghaememo.dto.SignupRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Users(SignupRequestDto signupRequestDto) {
        this.username = username;
        this.password = password;
    }
}
