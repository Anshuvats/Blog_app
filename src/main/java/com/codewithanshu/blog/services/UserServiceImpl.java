package com.codewithanshu.blog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codewithanshu.blog.config.AppConstants;
import com.codewithanshu.blog.entities.Role;
import com.codewithanshu.blog.entities.User;
import com.codewithanshu.blog.exceptions.ResourceNotFoundException;
import com.codewithanshu.blog.payloads.UserDto;
import com.codewithanshu.blog.repositories.RoleRepo;
import com.codewithanshu.blog.repositories.UserRepo;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;

	@Override
	public UserDto createUser(UserDto userDto) {
	   User user=this.modelMapper.map(userDto, User.class);
	   User savedUser=this.userRepo.save(user);
	   return this.modelMapper.map(savedUser, UserDto.class);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user=this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User"," id ",userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		
		User updatedUser = this.userRepo.save(user);
		return this.modelMapper.map(updatedUser, UserDto.class);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user=this.userRepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User", " Id ",userId));
		return this.modelMapper.map(user, UserDto.class);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User>users=this.userRepo.findAll();
		
		List<UserDto> userDtos=users.stream().map(user->this.modelMapper.map(user,UserDto.class)).collect((Collectors.toList()));
		
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User"," Id ",userId));
		
		this.userRepo.delete(user);
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user=this.modelMapper.map(userDto, User.class);
		
		//encoded the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		//roles
		Role role=this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		
		user.getRoles().add(role);
		
		User newUser=this.userRepo.save(user);
		
		return this.modelMapper.map(newUser, UserDto.class);
	}
	
}
