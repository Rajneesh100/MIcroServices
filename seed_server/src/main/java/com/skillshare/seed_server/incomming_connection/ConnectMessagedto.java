package com.skillshare.seed_server.incomming_connection;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;


public record ConnectMessagedto(
//        @NotNull(message = "pulicKey cannot be null")
        String publicKey,

//        @NotNull(message = "have some sense give a greeting")
        String greeting,

//        @NotNull(message = "endpoint cannot be null")
        String endPoint
) {
}
