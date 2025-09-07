package com.Management.university.Repository;

import com.Management.university.Models.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRep extends JpaRepository<Roles,Long> {
}
