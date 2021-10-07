package com.bridgelabz.fundoonotes.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {

	private int statusCode;
	
	private String statusMessage;
	
	private Object data;
}
