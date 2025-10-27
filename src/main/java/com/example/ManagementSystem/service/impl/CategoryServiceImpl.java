package com.example.ManagementSystem.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.ManagementSystem.dto.CategoryDTO;
import com.example.ManagementSystem.dto.Response;
import com.example.ManagementSystem.exception.NotFoundException;
import com.example.ManagementSystem.model.Category;
import com.example.ManagementSystem.repository.CategoryRepository;
import com.example.ManagementSystem.service.CategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response createCategory(CategoryDTO categoryDTO) {
        log.info("Category is, {}", categoryDTO);
        Category categoryToSave = modelMapper.map(categoryDTO, Category.class);
        categoryRepository.save(categoryToSave);

        return Response.builder()
                .status(200)
                .message("Category Saved Successfully")
                .build();
    }

    @Override
    public Response deleteCategory(Long id) {
        categoryRepository.findById(id).orElseThrow(() -> new NotFoundException(("Category Not Found")));
        categoryRepository.deleteById(id);

        return Response.builder()
                    .status(200)
                    .message("Category Was Successfully Deleted")
                    .build();
    }

    @Override
    public Response getAllCategories() {
        List<Category> categories = categoryRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        categories.forEach(category -> category.setProducts(null));

        List<CategoryDTO> categoryDTOList = modelMapper.map(categories, new TypeToken<List<CategoryDTO>>() {}.getType());
        return Response.builder()
                    .status(200)
                    .message("success")
                    .categories(categoryDTOList)
                    .build();
    }

    @Override
    public Response getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category Not Found"));
        CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
        return Response.builder()
                    .status(200)
                    .message("success")
                    .category(categoryDTO)
                    .build();
    }

    @Override
    public Response updateCategory(Long id, CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException(("Category Not Found")));
        existingCategory.setName(categoryDTO.getName());

        return Response.builder()
                    .status(200)
                    .message("Category Was Successfully Updated")
                    .category(categoryDTO)
                    .build();
    }

}
