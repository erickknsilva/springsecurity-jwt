package erickwck.springsecurity_webToken.controller;


import erickwck.springsecurity_webToken.controller.dto.ProductRequest;
import erickwck.springsecurity_webToken.controller.dto.ProductResponse;
import erickwck.springsecurity_webToken.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/products")
@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping
    public ProductResponse registerProduct(@RequestBody @Valid ProductRequest request, Authentication authentication) {
        return productService.registerProduct(request, authentication);
    }
}
