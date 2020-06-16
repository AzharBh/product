package com.geeks.product.controller;

import com.geeks.product.beans.UserBean;
import com.geeks.product.config.JWTService;
import com.geeks.product.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private JWTService jwtService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserBean user, HttpServletResponse response){
        if(user.getEmail() == null){
            return new ResponseEntity("Email is Null", HttpStatus.BAD_REQUEST);
        }
        if(user.getPassword() == null){
            return new ResponseEntity("Password is Null", HttpStatus.BAD_REQUEST);
        }

        UserBean user1 = userRepository.findByEmail(user.getEmail());
        if(user1 == null){
            return new ResponseEntity("User Not Found", HttpStatus.UNAUTHORIZED);
        }

        if(getPasswordEncoder().matches( user.getPassword() , user1.getPassword())) {

            HashMap<String, String> h = new HashMap<>();
            h.put("Authorization",jwtService.getToken(user));
            user1.setLogin(true);
            userRepository.save(user1);
            return ResponseEntity.ok(h);

        } else {

            return new ResponseEntity("Wrong Password", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity logout(@RequestBody UserBean user) {
        if (user.getEmail() == null) {
            return new ResponseEntity("Email is Null", HttpStatus.BAD_REQUEST);
        }
        if (user.getPassword() == null) {
            return new ResponseEntity("Password is Null", HttpStatus.BAD_REQUEST);
        }

        UserBean user1 = userRepository.findByEmail(user.getEmail());
        if (user1 == null) {
            return new ResponseEntity("User Not Found", HttpStatus.UNAUTHORIZED);
        }

        if (getPasswordEncoder().matches(user.getPassword(), user1.getPassword())) {
            user1.setLogin(false);
            userRepository.save(user1);
            return new ResponseEntity("Logout Successfully", HttpStatus.OK);
        }
        else return new ResponseEntity("Password Doesn't matched", HttpStatus.BAD_REQUEST);
    }

}
