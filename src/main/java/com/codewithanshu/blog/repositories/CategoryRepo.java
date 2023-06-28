package com.codewithanshu.blog.repositories;



import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithanshu.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category,Integer>{

}
