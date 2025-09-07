package com.Management.university.Models;

import jakarta.persistence.*;

@Entity
@Table(name="roles")
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="email")
    private String email;

    @Column(name="role")
    private String role;

    @ManyToOne
    @JoinColumn(name="user_id")
    private UnUser unUser;

    public Roles() {
    }

    public Roles(String email, String role, UnUser unUser) {
        this.email = email;
        this.role = role;
        this.unUser = unUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UnUser getUnUser() {
        return unUser;
    }

    public void setUnUser(UnUser unUser) {
        this.unUser = unUser;
    }
}
