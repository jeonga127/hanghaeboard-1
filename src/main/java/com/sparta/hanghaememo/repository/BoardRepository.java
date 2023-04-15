package com.sparta.hanghaememo.repository;

import com.sparta.hanghaememo.dto.BoardDTO;
import com.sparta.hanghaememo.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByOrderByModifiedAtDesc();

    Optional<Board> findByIdAndPassword(Long id, String password);

    Optional<Board> deleteByIdAndPassword(Long id, String password);

}
