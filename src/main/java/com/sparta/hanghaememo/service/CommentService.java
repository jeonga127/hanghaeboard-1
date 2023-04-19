package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.dto.CommentRequestDto;
import com.sparta.hanghaememo.dto.CommentResponseDTO;
import com.sparta.hanghaememo.dto.ResponseDTO;
import com.sparta.hanghaememo.entity.Board;
import com.sparta.hanghaememo.entity.Comment;
import com.sparta.hanghaememo.entity.UserRoleEnum;
import com.sparta.hanghaememo.entity.Users;
import com.sparta.hanghaememo.repository.BoardRepository;
import com.sparta.hanghaememo.repository.CommentRepository;
import com.sparta.hanghaememo.repository.UserRepository;
import com.sparta.hanghaememo.security.TokenProvider;
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
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public ResponseEntity createComment(CommentRequestDto requestDto, HttpServletRequest request){

        String username = usernameToken(request);
        // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        Users users = checkUsers(username);
        Board board = checkBoard(requestDto.getBoard_id());
        Comment comment = new Comment(users, board, requestDto);

        List<Comment> commentList = board.getCommentList();
        commentList.add(comment);
        board.addComment(commentList);

        commentRepository.save(comment);
        boardRepository.save(board);
        ResponseDTO responseDTO = ResponseDTO.setSuccess("댓글 작성 성공", new CommentResponseDTO(comment));
        return new ResponseEntity(responseDTO, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity updateComment(Long id, CommentRequestDto requestDto, HttpServletRequest request) {
        String username = usernameToken(request);
        // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        Users users = checkUsers(username);
        Comment comment = checkComment(id);
        // 작성자 게시글 체크
        isCommentUsers(users,comment);
        comment.update(requestDto);
        ResponseDTO responseDTO = ResponseDTO.setSuccess("댓글 수정 성공", new CommentResponseDTO(comment));
        return new ResponseEntity(responseDTO, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity deleteComment(Long id, HttpServletRequest request) {
        String username = usernameToken(request);
        // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        Users users = checkUsers(username);

        Comment comment = checkComment(id);
        // 작성자 게시글 체크
        isCommentUsers(users,comment);

        commentRepository.deleteById(id);
        ResponseDTO responseDTO = ResponseDTO.setSuccess("댓글 삭제 성공", null);
        return new ResponseEntity(responseDTO , HttpStatus.OK);
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

    private Comment checkComment(Long id){
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        );
        return comment;
    }

    private void isCommentUsers(Users users, Comment comment) {
        if (!comment.getUser().equals(users) && !users.getRole().equals(UserRoleEnum.ADMIN)) {
            throw new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다.");
        }
    }

    private String usernameToken(HttpServletRequest request){
        String token = tokenProvider.resolveToken(request);
        return tokenProvider.validate(token);
    }

}
