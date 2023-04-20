package com.sparta.hanghaememo.controller;

import com.sparta.hanghaememo.dto.board.BoardRequestDTO;
import com.sparta.hanghaememo.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/api/boards")
    public ResponseEntity write(@RequestBody BoardRequestDTO board, HttpServletRequest request){
        return boardService.write(board, request);
    }

    @GetMapping("/api/boards")
    public ResponseEntity list(){
        return boardService.list();
    }

    @GetMapping("/api/boards/{id}")
    public ResponseEntity listOne(@PathVariable Long id){
        return boardService.listOne(id);
    }

    @PutMapping("/api/boards/{id}")
    public ResponseEntity updateMemo(@PathVariable Long id, @RequestBody BoardRequestDTO boardDTO, HttpServletRequest request) {
        return boardService.update(id, boardDTO ,request);
    }

    @DeleteMapping("/api/boards/{id}")
    public ResponseEntity delete(@PathVariable Long id, HttpServletRequest request) {
        return boardService.delete(id, request);
    }
}
