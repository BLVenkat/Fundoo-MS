package com.bridgelabz.fundoonotes.dto;



import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @ApiModelProperty(notes = "first name of user",example = "Venkat",required = true)
	@NotBlank(message = "first name cannot be blank")
	private String firstName;

	@NotBlank(message = "last name cannot be blank")
	private String lastName;

	@NotBlank(message = "moblie number cannot be blank")
	private String mobileNumber;

	@NotBlank(message = "email cannot be blank")
	private String email;

	@NotBlank(message = "password cannot be blank")
	private String password;

}
