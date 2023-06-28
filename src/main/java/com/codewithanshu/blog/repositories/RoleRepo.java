package com.codewithanshu.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithanshu.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role,Integer>{

}
