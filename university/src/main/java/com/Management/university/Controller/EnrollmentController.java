package com.Management.university.Controller;

import com.Management.university.Models.Courses;
import com.Management.university.Models.UnUser;
import com.Management.university.Repository.CoursesRep;
import com.Management.university.Repository.UserRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    @Autowired
    CoursesRep coursesRep;

    @Autowired
    UserRep userRep;

    @GetMapping("/list")
    public ResponseEntity<Object> list()
    {
        return new ResponseEntity<>(coursesRep.findAll(), HttpStatus.OK);
    }

    @GetMapping("/register/{id}")
    @PreAuthorize("hasRole('Student')")
    public ResponseEntity<Object> register(@PathVariable Long id)
    {
        Courses course=coursesRep.findById(id).orElse(null);
        if(course==null)
            return new ResponseEntity<>("Course not found",HttpStatus.BAD_REQUEST);
        else
        {
            UnUser user=getId();
            if(user==null)
            {
                return new ResponseEntity<>("User Authorization failed",HttpStatus.BAD_REQUEST);
            }
            try
            {
                List<String> courseId=user.getCourses().stream().map(Courses::getCode).collect(Collectors.toList());
                if(courseId.contains(course.getCode()))
                    return new ResponseEntity<>("Student already enrolled in this course",HttpStatus.CONFLICT);

                user.addCourse(course);
                userRep.save(user);
                return new ResponseEntity<>("Student enrolled in this course",HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Failed to enroll student in this course"+ e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    }
    @GetMapping("list/student/{id}")
    public ResponseEntity<Object> listStudentId(@PathVariable Long id)
    {
        UnUser user=userRep.findById(id).orElse(null);
        if(user==null)
            return new ResponseEntity<>("User not found",HttpStatus.BAD_REQUEST);
        else if(user.getRoles().stream().anyMatch(role->role.getRole().equals("ROLE_Student")))
        {
            return new ResponseEntity<>(user.getCourses(),HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>("User is not a student",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("list/course/{id}")
    public ResponseEntity<Object> listCourseId(@PathVariable Long id)
    {
        Courses course=coursesRep.findById(id).orElse(null);
        if(course==null)
            return new ResponseEntity<>("Course not found",HttpStatus.BAD_REQUEST);
        else
        {
            List<UnUser> users=course.getUusers().stream().filter(o->o.getRoles().stream().anyMatch(role->role.getRole().equals("ROLE_Student"))).collect(Collectors.toList());
            return new ResponseEntity<>(users,HttpStatus.OK);
        }
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('Student')")
    public ResponseEntity<Object> delete(@PathVariable Long id)
    {
        UnUser user=getId();
        if(user==null)
            return new ResponseEntity<>("User Authorization failed",HttpStatus.BAD_REQUEST);
        else {
            Courses course=coursesRep.findById(id).orElse(null);
            if(course==null)
            {
                return new ResponseEntity<>("Course not found",HttpStatus.BAD_REQUEST);
            }
            else
            {
                if(user.getCourses().contains(course)) {
                    user.getCourses().remove(course);
                    course.getUusers().remove(user);
                //    System.out.println(user.getCourses());
                 //   System.out.println(course.getUusers());
                    coursesRep.save(course);
                    userRep.save(user);
                    return new ResponseEntity<>("Student unenrolled from this course",HttpStatus.OK);

                }else
                    return new ResponseEntity<>("Student not enrolled in this course",HttpStatus.CONFLICT);

            }

        }
    }

    public UnUser getId()
    {
        String name= SecurityContextHolder.getContext().getAuthentication().getName();
        //  System.out.println("kjbjkbvjksfdbfkjd" +name);
        UnUser user=userRep.findByEmail(name);
        return user;
    }


}
