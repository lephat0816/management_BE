package com.example.ManagementSystem.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ManagementSystem.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
