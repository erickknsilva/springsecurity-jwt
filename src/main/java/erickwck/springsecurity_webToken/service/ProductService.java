package erickwck.springsecurity_webToken.service;

import erickwck.springsecurity_webToken.controller.dto.ProductRequest;
import erickwck.springsecurity_webToken.controller.dto.ProductResponse;
import erickwck.springsecurity_webToken.controller.dto.ProductResponseListAll;
import erickwck.springsecurity_webToken.entity.Product;
import erickwck.springsecurity_webToken.entity.User;
import erickwck.springsecurity_webToken.repositories.ProductRepository;
import erickwck.springsecurity_webToken.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static erickwck.springsecurity_webToken.entity.Authority.Values.EXP_DELETE;
import static erickwck.springsecurity_webToken.entity.Authority.Values.EXP_READ_ANY;
import static erickwck.springsecurity_webToken.entity.Role.Values.ROLE_MANAGER;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }


    public ProductResponse registerProduct(ProductRequest request, Authentication authentication) {

        var user = getUserAuthenticate(authentication);

        var productSave = productRepository.findByNameAndDescriptionEqualsIgnoreCase(request.name(), request.description())
                .orElseGet(() -> productRepository.save(new Product(null, request.name(), request.price(), request.description(),
                        request.typeProduct(), user)));

        return ProductResponse.entityFromProductResponse(productSave);
    }



    public List<ProductResponseListAll> listAllProducts() {

        return productRepository.findAll().stream()
                .map(ProductResponseListAll::productResponseListAll)
                .toList();
    }

    public List<Product> listAllProductsManager(Authentication authentication) {

        var authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        if (authorities.contains(EXP_READ_ANY) || authorities.contains(EXP_DELETE)) {
            return productRepository.findAll();
        }

        return productRepository.findAllByOwnerUsernameEqualsIgnoreCase(authentication.getName());
    }

    private User getUserAuthenticate(Authentication authentication) {
        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario: " + authentication.getName() + " não encontrado."));
    }

}
