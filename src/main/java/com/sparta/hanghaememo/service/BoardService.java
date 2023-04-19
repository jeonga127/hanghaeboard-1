package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.dto.BoardRequestDTO;
import com.sparta.hanghaememo.dto.BoardResponseDto;
import com.sparta.hanghaememo.dto.ResponseDTO;
import com.sparta.hanghaememo.entity.Board;
import com.sparta.hanghaememo.entity.StatusEnum;
import com.sparta.hanghaememo.entity.Users;
import com.sparta.hanghaememo.jwt.JwtAuthenticationFilter;
import com.sparta.hanghaememo.jwt.JwtUtil;
import com.sparta.hanghaememo.repository.BoardRepository;
import com.sparta.hanghaememo.repository.UserRepository;
import com.sparta.hanghaememo.security.TokenProvider;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public ResponseEntity write(BoardRequestDTO boardRequestDTO , HttpServletRequest request){
        Board board = new Board(boardRequestDTO);

        // 사용자 정보 불러오기
        String username= usernameToken(request);
        // 회원 존재여부 확인
        Users users = checkUsers(username);

        board.addUser(users);
        boardRepository.save(board);
        return new ResponseEntity(boardRequestDTO, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    // Board 객체를 BoardResponseDTO 타입으로 변경해 리스트 반환
    public ResponseEntity list() {
        List<BoardResponseDto> boardList = boardRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(BoardResponseDto::new)
                .collect(Collectors.toList());
        ResponseDTO responseDTO =new ResponseDTO("list success", boardList);
        return new ResponseEntity(responseDTO, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity listOne(Long id) {
        // 게시글 존재여부 확인
        Board board = checkBoard(id);
        BoardResponseDto boardResponseDto = new BoardResponseDto(board);
        return new ResponseEntity(boardResponseDto, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity update(Long id, BoardRequestDTO boardRequestDTO, HttpServletRequest request) {

        // 사용자 정보 불러오기
        String username= usernameToken(request);
        // 회원 존재여부 확인
        Users users = checkUsers(username);
        // 게시글 존재여부 확인
        Board board = checkBoard(id);
        // 작성자 게시글 체크
        isBoardUsers(users, board);

        board.update(boardRequestDTO);
        return new ResponseEntity(boardRequestDTO, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity delete(Long id, HttpServletRequest request) {

        // 사용자 정보 불러오기
        String username= usernameToken(request);
        // 회원 존재여부 확인
        Users users = checkUsers(username);
        // 게시글 존재여부 확인
        Board board = checkBoard(id);
        // 작성자 게시글 체크
        isBoardUsers(users, board);

        boardRepository.deleteById(id);
        ResponseDTO responseDTO = new ResponseDTO("delete success",null);
        return new ResponseEntity(responseDTO, HttpStatus.OK);
    }

    private Users checkUsers(String username){
        Users users = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );
        return users;
    }

    private Board checkBoard(Long id){
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );
        return board;
    }

    private void isBoardUsers(Users users, Board board) {
        if (board.getUser() != users) {
            throw new IllegalArgumentException("다른 사람이 작성한 게시글은 삭제할 수 없습니다.");
        }
    }

    // 토큰 사용자 정보 가져오기
    private String usernameToken(HttpServletRequest request){
        String token = tokenProvider.resolveToken(request);
        return tokenProvider.validate(token);
    }
}
