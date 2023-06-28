package com.codewithanshu.blog.services;

import java.util.List;

import com.codewithanshu.blog.payloads.PostDto;
import com.codewithanshu.blog.payloads.PostResponse;

public interface PostService {

	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	PostDto updatePost(PostDto postDto,Integer postId);
	PostDto getPostById(Integer postId);
	PostResponse getAllPosts(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	void deletePost(Integer postId);
	
	List<PostDto>getPostsByCategory(Integer categoryId);
	List<PostDto>getPostsByUser(Integer userId);
	List<PostDto>searchPosts(String keyword);
}
