package com.Management.university.Controller;

import com.Management.university.DTO.LoginDTO;
import com.Management.university.DTO.RegisterDTO;
import com.Management.university.Models.Roles;
import com.Management.university.Utils.JWTUtil;
import com.Management.university.Models.UnUser;
import com.Management.university.Repository.UserRep;
import com.Management.university.Service.UniversityUDS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
public class UserController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRep userRep;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UniversityUDS  universityUDS;

    @Autowired
    JWTUtil jwtUtil;





    @PostMapping("/register")
    public ResponseEntity<String> reister(@RequestBody RegisterDTO registerDTO) {
        if(registerDTO==null)
        {
            return new ResponseEntity<>("User is null", HttpStatus.BAD_REQUEST);
        }
        Set<Roles> roles=new HashSet<>();

        UnUser user=userRep.findByEmail(registerDTO.getEmail());
        if(user!=null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
        }


        UnUser newuser=new UnUser();
        newuser.setName(registerDTO.getName());
        newuser.setEmail(registerDTO.getEmail());
        newuser.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        if(registerDTO.getRoles()!=null)
        {
            for(String role:registerDTO.getRoles())
            {
                roles.add(new Roles(registerDTO.getEmail(), role, newuser));
            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Roles is null");
        }

        newuser.setRoles(roles);
        userRep.save(newuser);
        System.out.println(newuser);
        return ResponseEntity.status(HttpStatus.OK).body("User registered successfully");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam String email) {
        UnUser user=userRep.findByEmail(email);
        System.out.println(email);
        if(user==null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
        }
        userRep.delete(user);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
    }

    @PostMapping("/signin")
    public ResponseEntity<Object> signin(@RequestBody LoginDTO loginDTO) {
        if(loginDTO==null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username or Password is null");
        }
        UnUser user=userRep.findByEmail(loginDTO.getEmail());
        if(user==null)
        {
            return new ResponseEntity<>("User does not exist",HttpStatus.BAD_REQUEST);
        }
        try
        {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword()));
            UserDetails userDetails=universityUDS.loadUserByUsername(loginDTO.getEmail());
            String token=jwtUtil.generateToken(userDetails);
            Map<String,Object> mp=new HashMap<>();
            mp.put("message", "Authentication Successfull");
            mp.put("role", userDetails.getAuthorities().toArray()[0]);
            mp.put("token", token);
            return ResponseEntity.status(HttpStatus.OK).body(mp);

        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid username or password",HttpStatus.UNAUTHORIZED);
        }

    }
}
