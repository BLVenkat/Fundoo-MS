package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.dto.LoginDTO;
import com.bridgelabz.fundoonotes.dto.UpdatePasswordDTO;
import com.bridgelabz.fundoonotes.dto.UserDTO;
import com.bridgelabz.fundoonotes.entity.User;

public interface UserService {

	public User register(UserDTO userDTO);
	
	public String login(LoginDTO loginDTO);
	
	public void verifyEmail(String token);
	
	public void forgotPassword(String email);
	
	public void resetPassword(String token, String password);
	
	public List<User> getAllUsers();
	
	public void updatePassword(String token,UpdatePasswordDTO updatePasswordDTO);
}
