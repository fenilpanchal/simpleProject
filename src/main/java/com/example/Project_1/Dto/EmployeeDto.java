package com.example.Project_1.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    private Integer id;

    @NotBlank(message = "name is required")
    private String username;

    @NotNull(message = "password is required")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotNull(message = "email is required")
    @Email(message = "invalid email format ,please Enter the proper format ")
    private String email;

    @NotNull(message = "role is required")
    @NotBlank(message = "Enter the role")
    private String role;

}