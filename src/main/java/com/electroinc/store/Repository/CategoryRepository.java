package com.electroinc.store.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electroinc.store.Entity.Category;

public interface CategoryRepository extends JpaRepository<Category, String> {
    List<Category> findByTitleContaining(String keyword);
}
