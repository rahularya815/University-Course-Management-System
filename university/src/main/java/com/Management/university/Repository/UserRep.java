package com.Management.university.Repository;

import com.Management.university.Models.UnUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRep extends JpaRepository<UnUser,Long> {
    UnUser findByEmail(String email);

    @Query("Select u from UnUser u join u.roles r where r.role = ?1")
    List<UnUser> findByRole(String role);

    @Query("Select u from UnUser u join u.roles r where r.role = ?1 and u.id = ?2")
    UnUser findByRoleandId(String role,Long id);


}
