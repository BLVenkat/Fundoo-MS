package com.bridgelabz.fundoonotes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.entity.Label;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.exception.FundooException;
import com.bridgelabz.fundoonotes.repository.LabelRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.utils.TokenService;

@Service
public class LabelServiceImp implements LabelService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private LabelRepository labelRepository;

	@Override
	public Label createLabel(String token, String labelName) {
		User user = userRepository.findById(tokenService.decodeToken(token))
				.orElseThrow(() -> new FundooException(HttpStatus.NOT_FOUND.value(), "User Not Found"));
		
		Optional<Label> islabelPresent = labelRepository.findByLabelName(labelName);
		if(islabelPresent.isPresent()) {
			throw new FundooException(HttpStatus.CONFLICT.value(), "Label already exit");
		}	
		
		Label label = new Label();
		label.setLabelName(labelName);
		Label savedLabel = labelRepository.save(label);
		user.getLabels().add(savedLabel);
		userRepository.save(user);
		return savedLabel;
	}

	@Override
	public List<Label> getLabels(String token) {
		User user = userRepository.findById(tokenService.decodeToken(token))
				.orElseThrow(() -> new FundooException(HttpStatus.NOT_FOUND.value(), "User Not Found"));
		return user.getLabels();
	}

	@Override
	public void deleteLabel(String token, Long labelId) {
		User user = userRepository.findById(tokenService.decodeToken(token))
				.orElseThrow(() -> new FundooException(HttpStatus.NOT_FOUND.value(), "User Not Found"));
		Label islabelPresent = user.getLabels().stream().filter((label)-> label.getId().equals(labelId)).findAny().orElseThrow(() -> new FundooException(HttpStatus.NOT_FOUND.value(), "label Not Found"));
		labelRepository.delete(islabelPresent);
	}

	@Override
	public Label updateLabel(String token, Long labelId, String labelName) {
		User user = userRepository.findById(tokenService.decodeToken(token))
				.orElseThrow(() -> new FundooException(HttpStatus.NOT_FOUND.value(), "User Not Found"));
		Label islabelPresent = user.getLabels().stream().filter((label)-> label.getId().equals(labelId)).findAny().orElseThrow(() -> new FundooException(HttpStatus.NOT_FOUND.value(), "label Not Found"));
		islabelPresent.setLabelName(labelName);
		return labelRepository.save(islabelPresent);
	}

}
