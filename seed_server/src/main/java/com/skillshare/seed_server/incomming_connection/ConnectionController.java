package com.skillshare.seed_server.incomming_connection;



import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
public class ConnectionController {

    @GetMapping ("/hi")
    public  String sayhi()
    {
        return "ping-pong";
    }
    @PostMapping("/find_me")
    public String sayHello(
            @Valid @RequestBody ConnectMessagedto dto

    )
    {
        if(dto.greeting()==null || dto.publicKey() ==null || dto.endPoint()==null)
        {
            return "send proper data";
        }
        else {
            // check public key in log_in service => make an api in loginservice to check for public key
            // and ping the client on his endPoint as it's just send it
            // if public key and endpoint active then add it to active list of client
            // in mongo db

            return "";
        }
    }


}
