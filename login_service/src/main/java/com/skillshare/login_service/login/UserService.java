package com.skillshare.login_service.login;
import org.springframework.data.util.Pair;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    public Pair<Users, String> saveNewUser(UserDto dto) {
        String message="";

        try {
            // Map DTO to Entity
            Users user = userMapper.toUser(dto);
            user.setLast_login();
//          user.setCreated_date(LocalDateTime.now());
            userRepository.save(user);
            message= "Success";
            return Pair.of(user, message);
        } catch (DataIntegrityViolationException ex) {
            // Handle unique constraint violation (email already exists)
            if (ex.getMessage().contains("email")) {
                message = "ERROR !! Email address is already in use.";
            }else {
                message = "Failed to save user: " + ex.getMessage();
            }
            return Pair.of(new Users(), message);
        } catch (Exception ex) {
            message= "Unexpected error occurred: " + ex.getMessage();
            return Pair.of(new Users(), message);
        }
    }

    public LocalDateTime GetLastLogin(String email)
    {
        Users user= this.userRepository.findByEmail(email);
        if(user==null)
        {
            return null;
        }
        return user.getLast_login();
    }
    public Users UpdateLastLogIn(String email)
    {
        Users user= this.userRepository.findByEmail(email);
        if(user==null)
        {
            return null;
        }
        var lastLogin = user.getLast_login();
        user.setLast_login();

        // Update the last login time
        user.setLast_login();

        // Save the updated user entity
        userRepository.save(user);


        System.out.println("Last login updated successfully.");
        return user;
    }

 public boolean isLastAuthStampEqual(Long id , String timestamp) {
        Optional<Users> user = this.userRepository.findById(id);

        // Check if user is present
        if (user.isPresent()) {
            return user.get().getLast_login().toString().equals(timestamp);
        }
        return false;
    }






}
