package com.electroinc.store.service.Impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.electroinc.store.Dto.CategoryDto;
import com.electroinc.store.Dto.PageableResponse;
import com.electroinc.store.Entity.Category;
import com.electroinc.store.Exception.ResourceNotFound;
import com.electroinc.store.Helper.helper;
import com.electroinc.store.Repository.CategoryRepository;
import com.electroinc.store.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CategoryDto CreateCategory(CategoryDto categoryDto) {
        String categoryId = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryId);
        Category category = mapper.map(categoryDto, Category.class);

        Category savedCategory = categoryRepository.save(category);
        return mapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto UpadateCategory(CategoryDto categoryDto, String categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFound("Given Category id is not found"));
        // category.setCategoryId(categoryDto.getCategoryId());
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category updateCategory = categoryRepository.save(category);
        return mapper.map(updateCategory, CategoryDto.class);
    }

    @Override
    public String DeleteCategory(String categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFound("Given Category id is not found"));
        categoryRepository.delete(category);
        return "Category Deleted !!";
    }

    @Override
    public PageableResponse<CategoryDto> GetAllCategory(int pagenumber, int pagesize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pagenumber, pagesize, sort);
        Page<Category> page = categoryRepository.findAll(pageable);
        PageableResponse<CategoryDto> response = helper.getPageableResponse(page, CategoryDto.class);
        return response;
    }

    @Override
    public CategoryDto GetSingleCategory(String categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFound("Given Category id is not found"));
        return mapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> SearchCategory(String keyword) {
        List<Category> titleCategory = categoryRepository.findByTitleContaining(keyword);
        List<CategoryDto> collect = titleCategory.stream().map((Category) -> mapper.map(Category, CategoryDto.class))
                .collect(Collectors.toList());
        return collect;
    }

}
