package com.BlogAppplicationBackend.blogAppApis.repositories;

import com.BlogAppplicationBackend.blogAppApis.entities.Category;
import com.BlogAppplicationBackend.blogAppApis.entities.Post;
import com.BlogAppplicationBackend.blogAppApis.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);
    List<Post> findByTitleContaining(String Keyword);
}
