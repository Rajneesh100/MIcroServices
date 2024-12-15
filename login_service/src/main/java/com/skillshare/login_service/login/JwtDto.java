package com.skillshare.login_service.login;

import jakarta.validation.constraints.NotEmpty;

public record JwtDto(
        @NotEmpty(message = "Email cannot be null")
        String jwtToken
) {
}
