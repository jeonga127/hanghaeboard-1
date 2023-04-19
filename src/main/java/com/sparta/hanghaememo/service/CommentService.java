package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.dto.CommentRequestDto;
import com.sparta.hanghaememo.dto.CommentResponseDTO;
import com.sparta.hanghaememo.entity.Board;
import com.sparta.hanghaememo.entity.Comment;
import com.sparta.hanghaememo.entity.Users;
import com.sparta.hanghaememo.jwt.JwtUtil;
import com.sparta.hanghaememo.repository.BoardRepository;
import com.sparta.hanghaememo.repository.CommentRepository;
import com.sparta.hanghaememo.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final BoardRepository boardRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponseDTO createComment(CommentRequestDto requestDto, HttpServletRequest request){
        Comment comment = new Comment(requestDto);

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            Users users = checkUsers(claims);
            Board board = checkBoard(requestDto.getBoard_id());

            comment.addUser(users);
            comment.addBoard(board);
            List<Comment> commentList = board.getCommentList();
            commentList.add(comment);
            board.addComment(commentList);
            commentRepository.save(comment);
            boardRepository.save(board);
            return new CommentResponseDTO(comment);
        }
        throw new IllegalArgumentException("GG");
    }

    @Transactional
    public CommentResponseDTO updateComment(Long id, CommentRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            Users users = checkUsers(claims);
            Comment comment = checkComment(id);

            if(users.getUsername().equals(comment.getUser().getUsername())){
                comment.update(requestDto);
                c
            }
            return new CommentResponseDTO(comment);
        }
        throw new IllegalArgumentException("GG");
    }

    @Transactional
    public CommentResponseDTO deleteComment(Long id, HttpServletRequest request) {


        return new CommentResponseDTO(null);
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

    private Comment checkComment(Long id){
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );
        return comment;
    }
}
