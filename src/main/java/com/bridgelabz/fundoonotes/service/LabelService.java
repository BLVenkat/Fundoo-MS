package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.entity.Label;

public interface LabelService {

	public Label createLabel(String token,String labelName);
	
	public List<Label> getLabels(String token);
	
	public void deleteLabel(String token,Long labelId);
	
	public Label updateLabel(String token,Long labelId,String labelName);
}
