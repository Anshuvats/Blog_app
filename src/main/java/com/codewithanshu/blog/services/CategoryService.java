package com.codewithanshu.blog.services;

import java.util.List;

import com.codewithanshu.blog.payloads.CategoryDto;

public interface CategoryService {

	CategoryDto createCategory(CategoryDto categoryDto);
	CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
	CategoryDto getCategoryById(Integer categoryId);
	List<CategoryDto>getAllCategories();
	void deleteCategory(Integer categoryId);
}
