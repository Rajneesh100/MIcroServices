package com.skillshare.seed_server.incomming_connection;

import org.springframework.stereotype.Service;

@Service
public class ConnectMessageMapper {
    public ConnectMessage toConnectMessage(ConnectMessagedto dto)
    {
        if(dto==null)
        {
            throw new NullPointerException("dto shouldn't be null");

        }

        var message= new ConnectMessage();
        message.setGreeting(dto.greeting());
        message.setEndPoint(dto.endPoint());
        message.setPublicKey(dto.publicKey());


        return message;



    }

}
