package com.electroinc.store.service;

import java.util.List;

import com.electroinc.store.Dto.CategoryDto;
import com.electroinc.store.Dto.PageableResponse;

public interface CategoryService {
    public CategoryDto CreateCategory(CategoryDto categoryDto);

    public CategoryDto UpadateCategory(CategoryDto categoryDto, String categoryId);

    public String DeleteCategory(String categoryId);

    public PageableResponse<CategoryDto> GetAllCategory(int pagenumber, int pagesize, String sortBy, String sortDir);

    public CategoryDto GetSingleCategory(String categoryId);

    public List<CategoryDto> SearchCategory(String keyword);
}
