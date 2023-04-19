package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.dto.BoardRequestDTO;
import com.sparta.hanghaememo.dto.BoardResponseDto;
import com.sparta.hanghaememo.dto.ResponseDTO;
import com.sparta.hanghaememo.entity.Board;
import com.sparta.hanghaememo.entity.StatusEnum;
import com.sparta.hanghaememo.entity.Users;
import com.sparta.hanghaememo.jwt.JwtUtil;
import com.sparta.hanghaememo.repository.BoardRepository;
import com.sparta.hanghaememo.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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

    @Transactional
    public BoardResponseDto write(BoardRequestDTO boardRequestDTO , HttpServletRequest request){
        Board board = new Board(boardRequestDTO);

        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 회원 존재여부 확인
            Users users = checkUsers(claims);

            board.addUser(users);
            boardRepository.save(board);
            return new BoardResponseDto(board);

        } else {
            throw new NoSuchElementException("로그인 하세요.");
        }
    }

    @Transactional(readOnly = true)
    // Board 객체를 BoardResponseDTO 타입으로 변경해 리스트 반환
    public ResponseDTO list() {
        List<BoardResponseDto> boardList = boardRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(BoardResponseDto::new)
                .collect(Collectors.toList());
        return new ResponseDTO("list success", StatusEnum.OK, boardList);
    }

    @Transactional(readOnly = true)
    public BoardResponseDto listOne(Long id) {
        // 게시글 존재여부 확인
        Board board = checkBoard(id);
        return new BoardResponseDto(board);
    }

    @Transactional
    public BoardResponseDto update(Long id, BoardRequestDTO boardRequestDTO, HttpServletRequest request) {

        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 회원 존재여부 확인
            Users users = checkUsers(claims);
            // 게시글 존재여부 확인
            Board board = checkBoard(id);
            // 작성자 게시글 체크
            isBoardUsers(users, board);

            board.update(boardRequestDTO);
            return new BoardResponseDto(board);

        } else {
            throw new NoSuchElementException("로그인 하세요.");
        }
    }

    @Transactional
    public ResponseDTO delete(Long id, HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 회원 존재여부 확인
            Users users = checkUsers(claims);
            // 게시글 존재여부 확인
            Board board = checkBoard(id);
            // 작성자 게시글 체크
            isBoardUsers(users, board);

            boardRepository.deleteById(id);
            return new ResponseDTO("delete success",StatusEnum.OK,null);
        } else {
            throw new NoSuchElementException("로그인 하세요.");
        }
    }

    private Users checkUsers(Claims claims){
        Users users = userRepository.findByUsername(claims.getSubject()).orElseThrow(
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
}
