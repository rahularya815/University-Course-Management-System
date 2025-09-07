package com.Management.university.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="courses")
public class Courses {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    String name;

    @Column(name="code")
    String code;

    @Column(name="description")
    String description;


    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "studentcoursejoin", 
        joinColumns = @JoinColumn(name = "course_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<UnUser> uusers = new HashSet<>();

    public Courses() {
        // Default constructor required by JPA
    }

    public Courses(String name, String code, String description) {
        this.name = name;
        this.code = code;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<UnUser> getUusers() {
        return uusers;
    }

    public void setUusers(Set<UnUser> uusers) {
        this.uusers = uusers;
    }

    public void addUser(UnUser user) {
        if (uusers == null) {
            uusers = new HashSet<>();
        }
        uusers.add(user);

    }
}
