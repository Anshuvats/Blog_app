package com.codewithanshu.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithanshu.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment,Integer>{

}
