package erickwck.springsecurity_webToken.controller.dto;

import erickwck.springsecurity_webToken.entity.Product;
import erickwck.springsecurity_webToken.entity.TypeProduct;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        BigDecimal price,
        String description,
        TypeProduct typeProduct,
        String ownerUsername
) {

    public static ProductResponse entityFromProductResponse(Product produto) {
        return new ProductResponse(
                produto.getId(),
                produto.getName(),
                produto.getPrice(),
                produto.getDescription(),
                produto.getTypeProduct(),
                produto.getOwner().getUsername()
        );
    }

}
