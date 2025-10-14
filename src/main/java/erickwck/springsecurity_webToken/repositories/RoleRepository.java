package erickwck.springsecurity_webToken.repositories;

import erickwck.springsecurity_webToken.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository
        extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
