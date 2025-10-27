package com.example.ManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ManagementSystem.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

    List<Product> findByNameContainingOrDescriptionContaining(String name, String description);
}
