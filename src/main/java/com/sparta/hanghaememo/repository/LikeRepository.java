package com.sparta.hanghaememo.repository;

import com.sparta.hanghaememo.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Likes, Long> {
    boolean existsByUserIdAndBoardId(Long user_id, Long board_id);
    Likes findByUserIdAndBoardId(Long user_id, Long board_id);
}
