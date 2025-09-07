package com.Management.university.Repository;

import com.Management.university.Models.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoursesRep extends JpaRepository<Courses,Long> {
    List<Courses> findAllByOrderByIdDesc();
}
