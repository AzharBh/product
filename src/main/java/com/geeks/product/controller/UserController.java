package com.geeks.product.controller;

import com.geeks.product.beans.UserBean;
import com.geeks.product.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity saveOrUpdate(@RequestBody UserBean user){
        if(user.getEmail() == null){
            return new ResponseEntity("Email is Null", HttpStatus.BAD_REQUEST);
        }
        else if(user.getPassword() == null){
            return new ResponseEntity("Password is Null", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(userRepository.save(user));
    }

    @GetMapping("{id}")
    public ResponseEntity getUser(@PathVariable Integer id){
        if(!userRepository.existsById(id)){
            return new ResponseEntity("User Not Found", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(userRepository.getOne(id));
    }

    @GetMapping
    public ResponseEntity getAllUsers(){
        return ResponseEntity.ok(userRepository.findAll());
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id){
        if(!userRepository.existsById(id)){
            return new ResponseEntity("User Not Found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Deleted Successfully", HttpStatus.OK);
    }
}
