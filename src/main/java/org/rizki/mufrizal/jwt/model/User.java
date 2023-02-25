package org.rizki.mufrizal.jwt.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String username;

    private String email;

    private String password;

    private Set<Role> roles = new HashSet<>();
}
