package com.example.onlineplatform.repository;

import com.example.onlineplatform.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdsCommentRepository extends JpaRepository<Comment, Integer> {
    Optional<Comment> findById(Integer id);
}
