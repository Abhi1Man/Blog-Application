package com.BlogAppplicationBackend.blogAppApis.repositories;

import com.BlogAppplicationBackend.blogAppApis.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
