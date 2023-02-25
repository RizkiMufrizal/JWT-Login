package org.rizki.mufrizal.jwt.service.impl;

import org.rizki.mufrizal.jwt.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userDetailsService")
public class UserService implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        org.rizki.mufrizal.jwt.model.User user = new org.rizki.mufrizal.jwt.model.User();
        user.setUsername("rizki");
        user.setPassword(new BCryptPasswordEncoder().encode("rizki"));
        user.setEmail("rizki@gmail.com");
        Role role = new Role();
        role.setName("ROLE_USER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        if (user.getUsername().equals(username)) {
            List<GrantedAuthority> authorities = buildUserAuthority(user.getRoles());
            return buildUserForAuthentication(user, authorities);
        }

        return null;
    }

    public User buildUserForAuthentication(org.rizki.mufrizal.jwt.model.User user, List<GrantedAuthority> grantedAuthorities) {
        return new User(user.getUsername(), user.getPassword(), true, true, true, true, grantedAuthorities);
    }

    public List<GrantedAuthority> buildUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        userRoles.forEach((userRole) -> grantedAuthorities.add(new SimpleGrantedAuthority(userRole.getName())));
        return new ArrayList<>(grantedAuthorities);
    }

}