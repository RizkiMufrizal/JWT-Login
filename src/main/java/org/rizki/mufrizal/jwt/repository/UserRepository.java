package org.rizki.mufrizal.jwt.repository;

import java.util.Optional;

import org.rizki.mufrizal.jwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u left join fetch u.roles pd where u.username = :username")
    Optional<User> findByUsername(@Param("username") String username);
}
