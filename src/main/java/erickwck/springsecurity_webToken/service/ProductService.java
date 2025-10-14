package erickwck.springsecurity_webToken.service;

import erickwck.springsecurity_webToken.controller.dto.ProductRequest;
import erickwck.springsecurity_webToken.controller.dto.ProductResponse;
import erickwck.springsecurity_webToken.entity.Product;
import erickwck.springsecurity_webToken.repositories.ProductRepository;
import erickwck.springsecurity_webToken.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }


    public ProductResponse registerProduct(ProductRequest request, Authentication authentication) {

        var user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario: " + authentication.getName() + " não encontrado."));

        var productSave = productRepository.findByNameAndDescriptionEqualsIgnoreCase(request.name(), request.description())
                .orElseGet(() -> productRepository.save(new Product(null, request.name(), request.price(), request.description(),
                        request.typeProduct(), user)));
        return ProductResponse.entityFromProductResponse(productSave);
    }
}
