package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.dto.CommentRequestDto;
import com.sparta.hanghaememo.dto.CommentResponseDTO;
import com.sparta.hanghaememo.dto.ResponseDTO;
import com.sparta.hanghaememo.entity.Board;
import com.sparta.hanghaememo.entity.Comment;
import com.sparta.hanghaememo.entity.StatusEnum;
import com.sparta.hanghaememo.entity.Users;
import com.sparta.hanghaememo.jwt.JwtUtil;
import com.sparta.hanghaememo.repository.BoardRepository;
import com.sparta.hanghaememo.repository.CommentRepository;
import com.sparta.hanghaememo.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final BoardRepository boardRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public ResponseEntity createComment(CommentRequestDto requestDto, HttpServletRequest request){
        Comment comment = new Comment(requestDto);

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else throw new IllegalArgumentException("토큰이 유효하지 않습니다");

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
            ResponseDTO responseDTO = new ResponseDTO("Comment Success", comment);
            return new ResponseEntity(responseDTO, HttpStatus.OK);
        }
        throw new IllegalArgumentException("토큰이 존재하지 않습니다");
    }

    @Transactional
    public ResponseEntity updateComment(Long id, CommentRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else return new ResponseEntity(new ResponseDTO("토큰이 유효하지 않습니다", null), HttpStatus.BAD_REQUEST);

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            Users users = checkUsers(claims);
            Comment comment = checkComment(id);

            if(users.getUsername().equals(comment.getUser().getUsername())){
                comment.update(requestDto);
                return new ResponseEntity(new ResponseDTO("수정을 성공했습니다", comment), HttpStatus.OK);
            } else return new ResponseEntity(new ResponseDTO("수정을 실패했습니다",null),  HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(new ResponseDTO("토큰이 존재하지 않습니다",null), HttpStatus.BAD_REQUEST);
    }

    @Transactional
    public ResponseEntity deleteComment(Long id, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else return new ResponseEntity("토큰이 유효하지 않습니다", HttpStatus.BAD_REQUEST);

            Users users = checkUsers(claims);
            Comment comment = checkComment(id);

            if(users.getUsername().equals(comment.getUser().getUsername())){
                commentRepository.deleteById(id);
                return new ResponseEntity( new ResponseDTO("댓글이 삭제되었습니다", null), HttpStatus.OK);
            } else return new ResponseEntity( new ResponseDTO("삭제 권한이 없습니다", null), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("토큰이 존재하지 않습니다", HttpStatus.BAD_REQUEST);
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
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        );
        return comment;
    }
}
