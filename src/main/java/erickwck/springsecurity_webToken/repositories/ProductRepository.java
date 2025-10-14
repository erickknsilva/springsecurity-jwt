package erickwck.springsecurity_webToken.repositories;

import erickwck.springsecurity_webToken.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository
        extends JpaRepository<Product, Long> {


    Optional<Product> findByNameAndDescriptionEqualsIgnoreCase(String name, String description);
}
