package com.example.ManagementSystem.service;

import org.springframework.web.multipart.MultipartFile;

import com.example.ManagementSystem.dto.ProductDTO;
import com.example.ManagementSystem.dto.Response;

public interface ProductService {
    
    Response saveProduct(ProductDTO productDTO, MultipartFile imageFile);

    Response updateProduct(ProductDTO productDTO, MultipartFile imagFile);

    Response getAllProducts();

    Response getProductById(Long id);
    
    Response deleteProduct(Long id);

    Response searchProduct(String input);
}
