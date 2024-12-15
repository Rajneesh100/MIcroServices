package com.skillshare.login_service.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserDto(
    @Email(message = "Invalid email address")
    @NotEmpty(message = "Email cannot be null")
    String email,
    @JsonProperty(defaultValue = "false")
    boolean with_otp,
    @JsonProperty(defaultValue = "false")
    boolean is_new,
    String otp
) {
}