package com.sm.society_management.controllers;

import com.sm.society_management.dto.LoginDto;
import com.sm.society_management.dto.SignupDto;
import com.sm.society_management.models.User;
import com.sm.society_management.repositories.UserRepo;
import com.sm.society_management.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")

public class UserController {

    @Autowired
    UserRepo userRepo;


    @PostMapping("/signup")
    public String signup(@RequestBody SignupDto dto) {
        if (userRepo.findByEmail(dto.getEmail()) != null) {
            return "Email already exists";
        }
        User user = new User();
        user.setName(dto.getName());
        user.setFlatNumber(dto.getFlatNumber());
        user.setPhone(dto.getPhone());
        user.setEmail(dto.getEmail());
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setWing(dto.getWing());
        user.setRole("RESIDENT");

        userRepo.save(user);
        return "signup successful";

    }

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/login")
    public String login(@RequestBody LoginDto dto) {

        User user = userRepo.findByEmail(dto.getEmail());
        if (user == null) return "User not found";

        if (!encoder.matches(dto.getPassword(), user.getPassword())) {
            return "Password incorrect";
        }



        return jwtUtil.generateToken(user.getEmail(),user.getRole());
}


    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @PostMapping("/register")
    public String register(@RequestBody User user)
    {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
        return "signup successful";
    }

    @GetMapping("/admin/residents/wing/{wing}")
    public List<User> getResidentsByWing(@PathVariable String wing){
        return userRepo.findByWing(wing);
    }

    /*@GetMapping("/create-admin")
    public String createAdmin() {
        User u = new User();
        u.setName("Society Admin");
        u.setEmail("admin@society.com");
        u.setPassword(encoder.encode("admin123"));
        u.setRole("ADMIN");
        u.setFlatNumber("ADMIN-001");   // ✅ ADD THIS
        u.setPhone("9999999999");       // if phone also non-null
        userRepo.save(u);
        return "Admin created";
    }*/


}
