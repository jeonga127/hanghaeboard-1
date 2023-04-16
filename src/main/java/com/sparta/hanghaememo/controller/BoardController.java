package com.sparta.hanghaememo.controller;

import com.sparta.hanghaememo.dto.BoardDTO;
import com.sparta.hanghaememo.dto.ResponseDTO;
import com.sparta.hanghaememo.entity.Board;
import com.sparta.hanghaememo.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/")
    public ModelAndView home(Board board){
        return new ModelAndView("index.html");
    }

    @PostMapping("/api/write")
    public ResponseDTO<Board> write(@RequestBody BoardDTO board){
        return boardService.write(board);
    }

    @GetMapping("/api/boards")
    public ResponseDTO<List<Board>> list(){
        return boardService.list();
    }

    @PutMapping("/api/boards/{id}")
    public ResponseDTO<Board> updateMemo(@PathVariable Long id, @RequestBody BoardDTO boardDTO) {
        return boardService.update(id, boardDTO);
    }

    @DeleteMapping("/api/boards/{id}")
    public ResponseDTO<Board> delete(@PathVariable Long id, @RequestBody BoardDTO boardDTO) {
        return boardService.delete(id, boardDTO.getPassword());
    }

}
