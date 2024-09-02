package com.example.communityservice.repository;

import com.example.communityservice.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByPostId(Long postId);
    Optional<Like> findByPostId(Long postId);
}
