package org.rizki.mufrizal.jwt.service.impl;

import org.rizki.mufrizal.jwt.model.Role;
import org.rizki.mufrizal.jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service("userDetailsService")
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<org.rizki.mufrizal.jwt.model.User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            List<GrantedAuthority> authorities = buildUserAuthority(user.get().getRoles());
            return buildUserForAuthentication(user.get(), authorities);
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