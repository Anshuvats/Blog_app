package com.codewithanshu.blog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithanshu.blog.entities.Category;
import com.codewithanshu.blog.exceptions.ResourceNotFoundException;
import com.codewithanshu.blog.payloads.CategoryDto;
import com.codewithanshu.blog.repositories.CategoryRepo;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category=this.modelMapper.map(categoryDto, Category.class);
		Category savedCategory=this.categoryRepo.save(category);
		return this.modelMapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category"," Category Id ",categoryId));
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category updateCategory=this.categoryRepo.save(category);
		return this.modelMapper.map(updateCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category"," Category Id ",categoryId));
		return this.modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category>categories=this.categoryRepo.findAll();
		List<CategoryDto>categoriesDto=categories.stream().map(category->this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
		return categoriesDto;
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category"," Category Id ",categoryId));
		this.categoryRepo.delete(category);
	}
	
}
