package org.carbonflux.security.repository;

import org.carbonflux.security.model.Users;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

   @EntityGraph(attributePaths = "authorities")
   Optional<Users> findOneWithAuthoritiesByUsername(String username);

   @EntityGraph(attributePaths = "authorities")
   Optional<Users> findOneWithAuthoritiesByEmailIgnoreCase(String email);

}
