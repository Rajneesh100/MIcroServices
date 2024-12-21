package com.skillshare.login_service.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.lang.NonNull;

public record UserDto(
    @Email(message = "Invalid email address")
    @NotEmpty(message = "Email cannot be null")
    String email,
    @JsonProperty(defaultValue = "false")
    boolean with_otp,
    @JsonProperty(defaultValue = "false")
    boolean is_new,
    @NotEmpty(message= "otp is null")
    String otp,
    String publicKey
) {
}