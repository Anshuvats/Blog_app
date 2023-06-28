package com.codewithanshu.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithanshu.blog.payloads.ApiResponse;
import com.codewithanshu.blog.payloads.CommentDto;
import com.codewithanshu.blog.services.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {
	
	@Autowired
	private CommentService commentService;

	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto>createComment(@RequestBody CommentDto comment,@PathVariable Integer postId){
		CommentDto createCommentDto=this.commentService.createComment(comment, postId);
		return new ResponseEntity<CommentDto>(createCommentDto,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse>deleteComment(@PathVariable Integer commentId){
		this.commentService.deleteComment(commentId);
		return ResponseEntity.ok(new ApiResponse("Comment deleted Successfully!!!",true));
	}
}
