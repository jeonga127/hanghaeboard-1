package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.dto.BoardDTO;
import com.sparta.hanghaememo.dto.ResponseDTO;
import com.sparta.hanghaememo.entity.Board;
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

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Transactional
    public ResponseDTO<Board> write(BoardDTO boardDTO , HttpServletRequest request){
        Board board = new Board(boardDTO);

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

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            Users user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new NoSuchElementException("사용자가 존재하지 않습니다.")
            );
            board.addUser(user);
            boardRepository.save(board);
            return ResponseDTO.setSuccess("write success",board);

        } else {
            return ResponseDTO.setFailed("write fail");
        }
    }

    @Transactional(readOnly = true)
    public ResponseDTO<List<Board>> list() {
        List<Board> boardList = boardRepository.findAllByOrderByModifiedAtDesc();
        return ResponseDTO.setSuccess("list success",boardList);
    }

    @Transactional(readOnly = true)
    public ResponseDTO<Board> listOne(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        return ResponseDTO.setSuccess("listOne success",board);
    }

    @Transactional
    public ResponseDTO<Board> update(Long id, BoardDTO boardDTO, HttpServletRequest request) {

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

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            Users user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            Board board = boardRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
            );
            board.update(boardDTO);
            return ResponseDTO.setSuccess("update success",board);

        } else {
            return ResponseDTO.setFailed("update fail");
        }
    }

    @Transactional
    public ResponseDTO<Board> delete(Long id, HttpServletRequest request) {
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

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            Users user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Board board = boardRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
            );

            boardRepository.deleteById(id);
            return ResponseDTO.setSuccess("delete success",null);
        } else {
            return ResponseDTO.setFailed("delete fail");
        }
    }
}
