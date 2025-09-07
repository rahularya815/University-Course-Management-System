package com.Management.university.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="un_user")
public class UnUser {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;

    @JsonIgnore
    @Column(name="password")
    private String password;



    @JsonIgnore
    @OneToMany(mappedBy = "unUser",fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST,CascadeType.REMOVE,CascadeType.REFRESH})
    private Set<Roles> roles;


    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,cascade={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.DETACH},
    mappedBy="uusers")
    private Set<Courses> courses;



    public UnUser() {
    }

    public UnUser(Long id, String name, String email, String password, Set<Roles> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public UnUser( String name, String email, String password, Set<Roles> roles) {

        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
    public UnUser( String name, String email) {

        this.name = name;
        this.email = email;
    }

    public UnUser(Long id, String name, String email, String password, Set<Roles> roles, Set<Courses> courses) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.courses = courses;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    public Set<Courses> getCourses() {
        return courses;
    }

    public void setCourses(Set<Courses> courses) {
        this.courses = courses;
    }

    public void addCourse(Courses course) {
        if (course == null) {
            return;
        }
        
        if (this.courses == null) {
            this.courses = new HashSet<>();
        }
        
        if (!this.courses.contains(course)) {
            this.courses.add(course);
            course.addUser(this);
        }
    }
    @Override
    public String toString() {
        return "UnUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}
