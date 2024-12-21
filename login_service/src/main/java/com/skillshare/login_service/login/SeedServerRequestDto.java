package com.skillshare.login_service.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record SeedServerRequestDto(

        @Email(message = "Invalid email address")
        @NotEmpty(message = "Email cannot be null")
        String email,
        @NotEmpty(message = "Email cannot be null")
        String publicKey
) {
}
