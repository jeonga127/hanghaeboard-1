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
        List<Board> boardList = new ArrayList<>();
        boardList = boardRepository.findAllByOrderByModifiedAtDesc();
        return ResponseDTO.setSuccess("success",boardList);
    }

    @Transactional
    public ResponseDTO<Board> update(Long id, BoardDTO boardDTO) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        Board board1 = boardRepository.findByIdAndPassword(id, boardDTO.getPassword()).orElseThrow(
                () -> new IllegalArgumentException("비밀번호가 일치하지 않습니다.")
        );
        board.update(boardDTO);
        return ResponseDTO.setSuccess("success", board);
    }

    @Transactional
    public ResponseDTO<Board> delete(Long id, String password) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        Board board1 = boardRepository.findByIdAndPassword(id, password).orElseThrow(
                () -> new IllegalArgumentException("비밀번호가 일치하지 않습니다.")
        );
        try {
            boardRepository.deleteByIdAndPassword(id, password);
        }catch (Exception e){
            return ResponseDTO.setFailed("Data Base Error!");
        }

        return ResponseDTO.setSuccess("success",null);
    }
}
