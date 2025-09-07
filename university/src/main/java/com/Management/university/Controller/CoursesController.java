package com.Management.university.Controller;

import com.Management.university.Models.Courses;
import com.Management.university.Models.UnUser;
import com.Management.university.Repository.CoursesRep;
import com.Management.university.Repository.UserRep;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;



@RestController
@RequestMapping("/courses")
public class CoursesController {

    @Autowired
    UserRep userRep;
    @Autowired
    CoursesRep coursesRep;
    @PostMapping("/register")
    @PreAuthorize("hasRole('Teacher')")
    public ResponseEntity<Object> registerCourse(@RequestBody Courses course) {

        if(course==null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid course details");
        }

        UnUser user=getId();
        if(user==null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
        }

        try {
            List<String> courseId=user.getCourses().stream().map(Courses::getCode).collect(Collectors.toList());
            if(courseId.contains(course.getCode()))
                return new ResponseEntity<>("Course already registered with user",HttpStatus.CONFLICT);
            user.addCourse(course);
            userRep.save(user);
            return ResponseEntity.status(HttpStatus.OK).body("Course registered successfully");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    @GetMapping("/list")
    public ResponseEntity<Object> list()
    {
        List<Courses> courses=coursesRep.findAllByOrderByIdDesc();
        return new ResponseEntity<>(courses,HttpStatus.OK);
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<Object> view(@PathVariable Long id)
    {
        Courses course=coursesRep.findById(id).orElse(null);;
        if(course==null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course not found");
        else
            return ResponseEntity.status(HttpStatus.OK).body(course);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('Teacher')")
    public  ResponseEntity<Object> delete(@PathVariable Long id)
    {
        Courses course=coursesRep.findById(id).orElse(null);
        if(course==null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course not found");
        else
        {
            coursesRep.delete(course);
            return ResponseEntity.status(HttpStatus.OK).body("Course deleted successfully");
        }
    }


    public UnUser getId()
    {
        String name=SecurityContextHolder.getContext().getAuthentication().getName();
      //  System.out.println("kjbjkbvjksfdbfkjd" +name);
        UnUser user=userRep.findByEmail(name);
        return user;
    }

}
