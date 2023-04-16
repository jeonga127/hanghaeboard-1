package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.dto.BoardDTO;
import com.sparta.hanghaememo.dto.ResponseDTO;
import com.sparta.hanghaememo.entity.Board;
import com.sparta.hanghaememo.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public ResponseDTO<Board> write(BoardDTO boardDTO){
        Board board = new Board(boardDTO);
        boardRepository.save(board);
        return ResponseDTO.setSuccess("success",board);
    }

    public ResponseDTO<List<Board>> list() {
        List<Board> boardList = boardRepository.findAllByOrderByModifiedAtDesc();
        return ResponseDTO.setSuccess("success",boardList);
    }

    public ResponseDTO<Board> listOne(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        return ResponseDTO.setSuccess("success",board);
    }

    @Transactional
    public ResponseDTO<Board> update(Long id, BoardDTO boardDTO) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        Board board1 = boardRepository.findByIdAndPassword(id, boardDTO.getPassword()).orElseThrow(
                () -> new IllegalArgumentException("비밀번호가 일치하지 않습니다.")
        );
//        if(board.getPassword().equals(boardDTO.getPassword())){
//            board.update(boardDTO);
//            return ResponseDTO.setSuccess("success",board);
//        }else return ResponseDTO.setFailed("fail");
        board.update(boardDTO);
        return ResponseDTO.setSuccess("success", board);
    }

    @Transactional
    public ResponseDTO<Board> delete(Long id, BoardDTO boardDTO) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        Board board1 = boardRepository.findByIdAndPassword(id, boardDTO.getPassword()).orElseThrow(
                () -> new IllegalArgumentException("비밀번호가 일치하지 않습니다.")
        );
//        if(board.getPassword().equals(boardDTO.getPassword())){
//            board.update(boardDTO);
//            return ResponseDTO.setSuccess("success",board);
//        }else return ResponseDTO.setFailed("fail");
        boardRepository.deleteByIdAndPassword(id, boardDTO.getPassword());
        return ResponseDTO.setSuccess("success",null);
    }
}
