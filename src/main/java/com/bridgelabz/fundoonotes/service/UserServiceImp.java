package com.bridgelabz.fundoonotes.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.LoginDTO;
import com.bridgelabz.fundoonotes.dto.UpdatePasswordDTO;
import com.bridgelabz.fundoonotes.dto.UserDTO;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.exception.FundooException;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.utils.EmailService;
import com.bridgelabz.fundoonotes.utils.TokenService;

@Service
public class UserServiceImp implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private TokenService tokenService;
	
	@Override
	public User register(UserDTO userDTO) {
		
		Optional<User> isUserPresent = getUser(userDTO.getEmail());
		
		if(isUserPresent.isPresent()) {
			throw new FundooException(HttpStatus.CONFLICT.value(),"User Email is already registered");
		}
		User user = new User();
		BeanUtils.copyProperties(userDTO, user);
		user.setIsEmailVerifed(false);
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		User savedUser = userRepository.save(user);
		String token = tokenService.createToken(savedUser.getId(), new Date (System.currentTimeMillis()+( 180 * 1000)));
		boolean isEmailSent = emailService.sendMail(savedUser.getEmail(), "venky70662@gmail.com", "Email Verification Link", "http://localhost:8080/user/verify-email/"+token);
		if(!isEmailSent) {
			throw new FundooException(HttpStatus.BAD_REQUEST.value(),"User is registered,but error while sending mail");
		}
		return savedUser;
	}

	@Override
	public String login(LoginDTO loginDTO) {
		Optional<User> isUserPresent =  getUser(loginDTO.getEmail());
		if(!isUserPresent.isPresent()) {
			throw new FundooException(HttpStatus.NOT_FOUND.value(), "Emailid is not registered");
		}
		if(!isUserPresent.get().getIsEmailVerifed()) {
			throw new FundooException(HttpStatus.UNAUTHORIZED.value(), "Email is not verified");
		}
		if(!(isUserPresent.get().getEmail().equals(loginDTO.getEmail())&& passwordEncoder.matches(loginDTO.getPassword(), isUserPresent.get().getPassword()))) {
			throw new FundooException(HttpStatus.BAD_REQUEST.value(), "Email or Password is wrong");
		}
		return tokenService.createToken(isUserPresent.get().getId());
	}
	
	public Optional<User> getUser(String email){
		return userRepository.findByEmail(email);
	}

	@Override
	public void verifyEmail(String token) {
		Long userId = tokenService.decodeToken(token);
		User user = userRepository.findById(userId).orElseThrow(() -> new FundooException(HttpStatus.NOT_FOUND.value(), "User Not Found"));
		user.setIsEmailVerifed(true);
		userRepository.save(user);
	}

	@Override
	public void forgotPassword(String email) {
		User user = getUser(email).orElseThrow(()->  new FundooException(HttpStatus.NOT_FOUND.value(), "User Not Found") );
		String token = tokenService.createToken(user.getId(), new Date (System.currentTimeMillis()+( 180 * 1000)));
		boolean isEmailSent = emailService.sendMail(user.getEmail(), "venky70662@gmail.com", "Reset password link", "http://localhost:8080/user/reset-password/"+token);
		if(!isEmailSent) {
			throw new FundooException(HttpStatus.BAD_REQUEST.value(),"error while sending mail");
		}
	}

	@Override
	public void resetPassword(String token, String password) {
		User user = userRepository.findById(tokenService.decodeToken(token)).orElseThrow(() -> new FundooException(HttpStatus.NOT_FOUND.value(), "User Not Found"));
		user.setPassword(passwordEncoder.encode(password));
		userRepository.save(user);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public void updatePassword(String token, UpdatePasswordDTO updatePasswordDTO) {
		User user = userRepository.findById(tokenService.decodeToken(token)).orElseThrow(() -> new FundooException(HttpStatus.NOT_FOUND.value(), "User Not Found"));
		if(!passwordEncoder.matches(updatePasswordDTO.getOldPassword(), user.getPassword())) {
			throw new FundooException(HttpStatus.UNAUTHORIZED.value(), "Old and new password is not matching");
		}
		user.setPassword(passwordEncoder.encode(updatePasswordDTO.getNewPassword()));
		userRepository.save(user);
	}

}
