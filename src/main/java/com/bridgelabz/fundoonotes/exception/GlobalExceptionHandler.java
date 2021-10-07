package com.bridgelabz.fundoonotes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bridgelabz.fundoonotes.response.Response;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(FundooException.class)
	public ResponseEntity<Response> handleFundooException(FundooException exception){
		Response errorResponse = new Response(exception.getStatusCode(),exception.getStatusMessage(),null);
		return new ResponseEntity<Response>(errorResponse,HttpStatus.BAD_GATEWAY);
	}
}
