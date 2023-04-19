package com.sparta.hanghaememo.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupRequestDto {

    @Size(min = 4, max = 10)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])[a-z0-9]*$" , message = "아이디 형식이 맞지않음")
    private String username;

    @Size(min = 8, max = 15)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]*$" , message = "비밀번호 형식이 맞지않음")
    private String password;
    private boolean admin = false;
    private String adminToken = "";
}
