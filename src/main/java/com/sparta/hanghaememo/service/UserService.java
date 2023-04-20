package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.dto.ResponseDTO;
import com.sparta.hanghaememo.dto.user.LoginRequestDto;
import com.sparta.hanghaememo.dto.user.SignupRequestDto;
import com.sparta.hanghaememo.entity.StatusEnum;
import com.sparta.hanghaememo.entity.UserRoleEnum;
import com.sparta.hanghaememo.entity.Users;
import com.sparta.hanghaememo.jwt.JwtUtil;
import com.sparta.hanghaememo.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    public ResponseEntity signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        // 회원 중복 확인
        Optional<Users> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            return new ResponseEntity(ResponseDTO.setFail("중복된 username 입니다", StatusEnum.BAD_REQUEST), HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST);
        }

        // 사용자 role 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        Users user = new Users(username, password, role);
        userRepository.save(user);
        ResponseDTO responseDTO = ResponseDTO.setSuccess("회원가입 성공", StatusEnum.OK, null);
        return new ResponseEntity(responseDTO, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 유무 확인
        Users user = userCheck(username);

        // 비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return new ResponseEntity(ResponseDTO.setFail("회원을 찾을 수 없습니다.", StatusEnum.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
        response.addHeader(jwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
        ResponseDTO responseDTO = ResponseDTO.setSuccess("로그인 성공", StatusEnum.OK, user);
        return new ResponseEntity(responseDTO, HttpStatus.OK);
    }

    private Users userCheck(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("회원을 찾을 수 없습니다.")
        );
    }
}