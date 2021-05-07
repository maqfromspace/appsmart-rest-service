package com.maqfromspace.appsmartrestservice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Authentication response body
 */
@ApiModel(value = "AuthenticationResponseDto", description = "Token")
@Data
public class AuthenticationResponseDto {
    @ApiModelProperty(notes = "username", example = "admin", required = true)
    @NotNull(message = "Field 'username' can't be null")
    private String username;
    @ApiModelProperty(notes = "token", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJNYWtzaW0iLCJyb2xlcyI6WyJBRE1JTl9ST0xFIl0sImlhdCI6MTYyMDM2NzU3MywiZXhwIjoxNjIwMzcxMTczfQ.SziBQ_isaV3U8BHpL3Ytrowj7vkHtl8Ekyt1AABGoto", required = true)
    @NotNull
    private String token;
}
