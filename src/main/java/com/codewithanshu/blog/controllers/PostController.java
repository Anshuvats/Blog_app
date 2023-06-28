package com.codewithanshu.blog.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codewithanshu.blog.config.AppConstants;
import com.codewithanshu.blog.payloads.ApiResponse;
import com.codewithanshu.blog.payloads.PostDto;
import com.codewithanshu.blog.payloads.PostResponse;
import com.codewithanshu.blog.services.FileService;
import com.codewithanshu.blog.services.PostService;


@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	@Autowired
	private String path;
	
	//create
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto>createPost(@RequestBody PostDto postDto,@PathVariable Integer userId,@PathVariable Integer categoryId){
		PostDto createPostDto=this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPostDto,HttpStatus.CREATED);
	}
	
	//update 
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto>updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId){
		return ResponseEntity.ok(this.postService.updatePost(postDto, postId));
	}
	
	//get by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>>getPostsByUser(@PathVariable Integer userId){
		return ResponseEntity.ok(this.postService.getPostsByUser(userId));
	}
	
	//get by category
	@GetMapping("category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>>getPostsByCategory(@PathVariable Integer categoryId){
		return ResponseEntity.ok(this.postService.getPostsByCategory(categoryId));
	}
	
	//get by postId
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto>getPostById(@PathVariable Integer postId){
		return ResponseEntity.ok(this.postService.getPostById(postId));
	}
	
	//get by All
	@GetMapping("/posts")
	public ResponseEntity<PostResponse>getAllPosts(
			@RequestParam(value="pageNumber",defaultValue=AppConstants.PAGE_NUMBER,required=false) Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue=AppConstants.PAGE_SIZE,required=false) Integer pageSize,
			@RequestParam(value="sortBy",defaultValue=AppConstants.SORTBY,required=false) String sortBy,
			@RequestParam(value="sortDir",defaultValue=AppConstants.SORTDIR,required=false) String sortDir
			){
		return ResponseEntity.ok(this.postService.getAllPosts(pageNumber,pageSize,sortBy,sortDir));
	}
	
	//Delete
	@DeleteMapping("posts/{postId}")
	public ResponseEntity<ApiResponse>deletePost(@PathVariable Integer postId){
		this.postService.deletePost(postId);
		return ResponseEntity.ok(new ApiResponse("Post deleted successfully!",true));
	}
	
	//Search
	@GetMapping("posts/search/{keyword}")
	public ResponseEntity<List<PostDto>>searchPosts(@PathVariable String keyword){
		return ResponseEntity.ok(this.postService.searchPosts(keyword));
	}
	
	//post image upload
	@PostMapping("post/image/upload/{postId}")
	public ResponseEntity<PostDto>uploadPostImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId
			) throws IOException{
		PostDto postDto=this.postService.getPostById(postId);
		String fileName=this.fileService.uploadImage(path, image);
		postDto.setImageName(fileName);
		return ResponseEntity.ok(this.postService.updatePost(postDto, postId));
	}
//	
//	//method to serve files
//	@GetMapping(value="post/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
//	public void downloadImage(
//			@PathVariable("imageName")String imageName,
//			HttpServletResponse response) throws IOException {
//		InputStream resource=this.fileService.getResource(path, imageName);
//		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
//		StreamUtils.copy(resource, resource.getOutputStream());
//	}
}
