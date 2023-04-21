package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.dto.ResponseDTO;
import com.sparta.hanghaememo.dto.comment.CommentRequestDto;
import com.sparta.hanghaememo.dto.comment.CommentResponseDTO;
import com.sparta.hanghaememo.entity.*;
import com.sparta.hanghaememo.jwt.JwtUtil;
import com.sparta.hanghaememo.repository.BoardRepository;
import com.sparta.hanghaememo.repository.CommentRepository;
import com.sparta.hanghaememo.repository.UserRepository;
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
    private final CommentRepository commentRepository;


    @Transactional
    public ResponseEntity createComment(CommentRequestDto requestDto, Users user){
        Board board = checkBoard(requestDto.getBoard_id());
        Comment comment = new Comment(user, board, requestDto);

        List<Comment> commentList = board.getCommentList();
        commentList.add(comment);
        board.addComment(commentList);

        commentRepository.save(comment);
        boardRepository.save(board);
        ResponseDTO responseDTO = ResponseDTO.setSuccess("댓글 작성 성공", StatusEnum.OK, new CommentResponseDTO(comment));
        return new ResponseEntity(responseDTO, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity updateComment(Long id, CommentRequestDto requestDto, Users user) {
        Comment comment = checkComment(id);
        // 작성자 게시글 체크
        isCommentUsers(user,comment);
        comment.update(requestDto);
        ResponseDTO responseDTO = ResponseDTO.setSuccess("댓글 수정 성공", StatusEnum.OK, new CommentResponseDTO(comment));
        return new ResponseEntity(responseDTO, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity deleteComment(Long id, Users user) {
        Comment comment = checkComment(id);
        // 작성자 게시글 체크
        isCommentUsers(user,comment);

        commentRepository.deleteById(id);
        ResponseDTO responseDTO = ResponseDTO.setSuccess("댓글 삭제 성공", StatusEnum.OK, null);
        return new ResponseEntity(responseDTO , HttpStatus.OK);
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

    private void isCommentUsers(Users users, Comment comment) {
        if (!comment.getUser().getUsername().equals(users.getUsername()) && !users.getRole().equals(UserRoleEnum.ADMIN)) {
            throw new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다.");
        }
    }
}
