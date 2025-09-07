package com.Management.university.Service;

import com.Management.university.Models.Roles;
import com.Management.university.Models.UnUser;
import com.Management.university.Repository.UserRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UniversityUDS implements UserDetailsService {
    @Autowired
    UserRep userRep;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UnUser user=userRep.findByEmail(email);
        if(user==null)
        {
            throw new UsernameNotFoundException("Username not found");
        }

        List<GrantedAuthority> authority=user.getRoles().stream().map(o->(new SimpleGrantedAuthority(o.getRole()))).collect(Collectors.toList());;
        for(Roles roles:user.getRoles())
        {
          //  authority.add(new SimpleGrantedAuthority(roles.getRole()));
           // System.out.println(roles.getRole());
        }

        return new User(user.getEmail(), user.getPassword(), authority);
    }
}
