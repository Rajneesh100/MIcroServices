package com.skillshare.login_service.login;

import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public Users toUser(UserDto dto)
    {
        if(dto==null) {
            throw new NullPointerException("dto shouldn't be null");
        }
        System.out.println(dto.publicKey());
        var user= new Users();
        user.setPublicKey(dto.publicKey());
        user.setEmail(dto.email());
        return user;
    }
}

