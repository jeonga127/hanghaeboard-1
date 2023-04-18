package com.sparta.hanghaememo.controller;

import com.sparta.hanghaememo.dto.BoardRequestDTO;
import com.sparta.hanghaememo.dto.BoardResponseDto;
import com.sparta.hanghaememo.dto.ResponseDTO;
import com.sparta.hanghaememo.entity.Board;
import com.sparta.hanghaememo.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
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

    @PostMapping("/api/boards")
    public BoardResponseDto write(@RequestBody BoardRequestDTO board, HttpServletRequest request){
        return boardService.write(board, request);
    }

    @GetMapping("/api/boards")
    public ResponseDTO list(){
        return boardService.list();
    }

    @GetMapping("/api/boards/{id}")
    public BoardResponseDto listOne(@PathVariable Long id){
        return boardService.listOne(id);
    }

    @PutMapping("/api/boards/{id}")
    public BoardResponseDto updateMemo(@PathVariable Long id, @RequestBody BoardRequestDTO boardDTO, HttpServletRequest request) {
        return boardService.update(id, boardDTO ,request);
    }

    @DeleteMapping("/api/boards/{id}")
    public ResponseDTO delete(@PathVariable Long id, HttpServletRequest request) {
        return boardService.delete(id, request);
    }
}
