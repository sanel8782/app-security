package com.lideratec.appsecurity.user;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

//se conecta directamente con PostgreSQL
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
