package com.maqfromspace.appsmartrestservice.dto.authentication;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Authentication request body
 */
@Data
@ApiModel(value = "AuthenticationRequestDto", description = "User parameters")
public class AuthenticationRequestDto {
    @ApiModelProperty(notes = "username", example = "admin", required = true)
    @NotNull(message = "Field 'username' can't be null")
    private String username;
    @NotNull(message = "Field 'password' can't be null")
    @ApiModelProperty(notes = "password", example = "adminpassword", required = true)
    private String password;
}

