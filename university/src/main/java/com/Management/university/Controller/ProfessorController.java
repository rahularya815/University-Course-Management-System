package com.Management.university.Controller;

import com.Management.university.Models.UnUser;
import com.Management.university.Repository.UserRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/professors")
@RestController
public class ProfessorController {

    @Autowired
    UserRep userRep;

    @GetMapping("/list")
    public ResponseEntity<Object> list()
    {
        List<UnUser> users=userRep.findByRole("ROLE_Teacher");
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @GetMapping("/view/{id}")
    public ResponseEntity<Object> view(@PathVariable Long id)
    {
        UnUser user=userRep.findByRoleandId("ROLE_Teacher",id);
        if(user==null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
        else
            return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping("/edit/{id}")
    @PreAuthorize("hasRole('Teacher')")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody UnUser user)
    {
        UnUser user1=userRep.findByRoleandId("ROLE_Teacher",id);
        if (user1==null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not Found");
        }
        else
        {
            user1.setName(user.getName());
            UnUser user2=userRep.save(user1);
            return ResponseEntity.status(HttpStatus.OK).body(user2);
        }
    }
}
