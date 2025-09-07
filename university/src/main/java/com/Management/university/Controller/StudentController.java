package com.Management.university.Controller;

import com.Management.university.Models.UnUser;
import com.Management.university.Repository.UserRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    UserRep userRep;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/list")
    public ResponseEntity<Object> list()
    {
        List<UnUser> users=userRep.findByRole("ROLE_Student");
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<Object> view(@PathVariable Long id)
    {
        UnUser user=userRep.findByRoleandId("ROLE_Student",id);
        if(user==null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
        }
        else
            return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping("/edit/{id}")
    @PreAuthorize("hasRole('Student')")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody UnUser user)
    {
        UnUser user1=userRep.findByRoleandId("ROLE_Student",id);
        if(user1==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not exists.");
        else {
            user1.setName(user.getName());
        //    user1.setPassword(passwordEncoder.encode(user.getPassword()));
            UnUser user2=userRep.save(user1);
            return ResponseEntity.status(HttpStatus.OK).body(user2);
        }
    }
}
