package com.codewithanshu.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithanshu.blog.payloads.ApiResponse;
import com.codewithanshu.blog.payloads.CategoryDto;
import com.codewithanshu.blog.services.CategoryService;

@RestController
@RequestMapping("api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	//Post - Create 
	@PostMapping("/")
	public ResponseEntity<CategoryDto>createCategory(@Valid @RequestBody CategoryDto categoryDto){
		CategoryDto createCategoryDto=this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createCategoryDto,HttpStatus.CREATED);
	}
	
	//Put- Update 
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto>updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId){
		CategoryDto updateCategoryDto=this.categoryService.updateCategory(categoryDto, categoryId);
		return ResponseEntity.ok(updateCategoryDto);
	}
	
	//Delete
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse>deleteCategory(@PathVariable Integer categoryId){
		this.categoryService.deleteCategory(categoryId);
		return ResponseEntity.ok(new ApiResponse("Category deleted Successfully!!!",true));
	}
	
	//get
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto>getSingleUser(@PathVariable Integer categoryId){
		return ResponseEntity.ok(this.categoryService.getCategoryById(categoryId));
	}
	
	//getAll
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>>getAllCategory(){
		return ResponseEntity.ok(this.categoryService.getAllCategories());
	}
}
