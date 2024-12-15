package com.skillshare.login_service.login;

import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public Users toUser(UserDto dto)
    {
        if(dto==null) {
            throw new NullPointerException("dto shouldn't be null");
        }
        var user= new Users();
        user.setEmail(dto.email());
        return user;
    }
}

