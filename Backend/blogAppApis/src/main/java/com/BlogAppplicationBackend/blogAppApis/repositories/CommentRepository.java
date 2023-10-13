package com.BlogAppplicationBackend.blogAppApis.repositories;

import com.BlogAppplicationBackend.blogAppApis.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
}
