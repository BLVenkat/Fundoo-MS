package com.bridgelabz.fundoonotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.entity.Label;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.LabelService;

@RestController
@RequestMapping("/label")
public class LabelController {

	@Autowired
	private LabelService labelService;
	
	@PostMapping
	public ResponseEntity<Response> createLabel(@RequestHeader String token,@RequestParam String labelName){
		Label label = labelService.createLabel(token, labelName);
		return new ResponseEntity<Response>(new Response(HttpStatus.CREATED.value(), "Label created Successfully", label),HttpStatus.CREATED);
		
	}
	
	@GetMapping
	public ResponseEntity<Response> getLabels(@RequestHeader String token){
		List<Label> labels = labelService.getLabels(token);
		return new ResponseEntity<Response>(new Response(HttpStatus.OK.value(), "Labels retrived Successfully", labels),HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{labelId}")
	public ResponseEntity<Response> deleteLabel(@RequestHeader String token,@PathVariable Long labelId){
		labelService.deleteLabel(token, labelId);
		return new ResponseEntity<Response>(new Response(HttpStatus.OK.value(), "Labels deleted Successfully", ""),HttpStatus.OK);
		
	}
	

	@PutMapping("/{labelId}")
	public ResponseEntity<Response> editlabel(@RequestHeader String token,@PathVariable Long labelId,@RequestParam String labelName){
		Label label = labelService.updateLabel(token, labelId, labelName);
		return new ResponseEntity<Response>(new Response(HttpStatus.OK.value(), "Labels updated Successfully", label),HttpStatus.OK);
		
	}
	

}
