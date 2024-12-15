package com.skillshare.login_service.login;


import jakarta.validation.Valid;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private final EmailService emailService;
    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(EmailService emailService, UserService saveNewUserService, JwtService jwtService) {
        this.emailService = emailService;
        this.userService = saveNewUserService;
        this.jwtService =jwtService;
    }

    @GetMapping("/hello")
    public String sayHello() { return "hi";}

//generateJwtForUser
    @PostMapping("/validate")
    public String RegisterUser(
            @Valid @RequestBody UserDto dto
    ){
        String response ="";
        if(!dto.with_otp()){


            return emailService.sendOtpToEmail(dto.email());


        }else
        {
            boolean success = emailService.validateOtp(dto.email(), dto.otp());

            if(success)
            {
                if(dto.is_new())
                {
                   Pair<Users, String> p = userService.saveNewUser(dto);
                    System.out.println(p.getFirst().getId()+" "+  p.getFirst().getEmail()+" " +  p.getFirst().getLast_login() +" "+p.getSecond());
                    if(p.getSecond().equals("Success")) {
                        System.out.println("hi\n");
                        String tem= jwtService.generateToken(p.getFirst().getId(), p.getFirst().getEmail(), p.getFirst().getLast_login());
                        System.out.println(tem);
                       System.out.println("Registered Successful !! Welcome to skillshare :)  , Jwt token :" + jwtService.generateToken(p.getFirst().getId(), p.getFirst().getEmail(), p.getFirst().getLast_login()));
                       return "Registered Successful !! Welcome to skillshare :)  , Jwt token :" + jwtService.generateToken(p.getFirst().getId(), p.getFirst().getEmail(), p.getFirst().getLast_login());
                   }else
                   {
                       return p.getSecond();
                   }
                }else
                {
                    // without registered user even after otp validation throw error
                    Users user= userService.GetAndUpdateLastLogIn(dto.email());
                    if(user==null) return "user not found :( first register";

                    // else update last login time this will help in versioning tokens
                    System.out.println("Welcome back ,Last Log in :"+ user.getLast_login() +". New jwt token : "+ jwtService.generateToken(user.getId(),user.getEmail(),user.getLast_login()));
                    return "Welcome back ,Last Log in :"+ user.getLast_login() +". New jwt token : "+ jwtService.generateToken(user.getId(),user.getEmail(),user.getLast_login());

                }

            }
            // in all rest cases
            System.out.println("Failure Try again");
            return "Otp validation failure! Try again :)";

        }
    }

//    @PostMapping("/easyAuth")
//    public Pair< Boolean,String> validate(
//        @Valid  @RequestBody JwtDto token
//    ){
//       boolean isvalid = this.jwtService.validateToken(token.jwtToken());
//       if(isvalid) return Pair.of(true,token.jwtToken());
//       else return Pair.of(false,"");
//    }


    @PostMapping("/easyAuth")
    public ResponseEntity<Pair<Boolean, String>> validate(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        // Check if the Authorization header exists and starts with "Bearer "
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body(Pair.of(false, "Invalid Authorization header"));
        }

        // Extract the token from the Authorization header
        String token = authorizationHeader.substring(7); // Remove "Bearer " prefix

        // Validate the token
        boolean isValid = this.jwtService.validateToken(token);

        // Return response based on validation result
        if (isValid) {
            return ResponseEntity.ok(Pair.of(true, token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Pair.of(false, ""));
        }
    }


}
