package erickwck.springsecurity_webToken.repositories;

import erickwck.springsecurity_webToken.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

   Optional<User> findByUsername(String username);
}
