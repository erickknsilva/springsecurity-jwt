package erickwck.springsecurity_webToken.controller;


import erickwck.springsecurity_webToken.controller.dto.ProductRequest;
import erickwck.springsecurity_webToken.controller.dto.ProductResponse;
import erickwck.springsecurity_webToken.controller.dto.ProductResponseListAll;
import erickwck.springsecurity_webToken.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static erickwck.springsecurity_webToken.entity.Authority.Values.*;

@RequestMapping("/products")
@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping
    @PreAuthorize("hasAuthority('" + EXP_CREATE + "')")
    public ProductResponse registerProduct(@RequestBody @Valid ProductRequest request, Authentication authentication) {
        return productService.registerProduct(request, authentication);
    }

    @GetMapping
    public List<ProductResponseListAll> listAllProducts() {
        return productService.listAllProducts();
    }


    @GetMapping("/manager")
    @PreAuthorize("hasAuthority('" + EXP_READ_ANY + "') or hasAuthority('" + EXP_CREATE + "')")
    public List<ProductResponse> listAllProductsManager(Authentication authentication) {
        return  productService.listAllProductsManager(authentication).stream().map(ProductResponse::entityFromProductResponse).toList();
    }

}
