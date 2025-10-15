package erickwck.springsecurity_webToken.controller.dto;

import erickwck.springsecurity_webToken.entity.Product;
import erickwck.springsecurity_webToken.entity.TypeProduct;

import java.math.BigDecimal;

public record ProductResponseListAll(
        Long id,
        String name,
        BigDecimal price,
        String description,
        TypeProduct typeProduct
) {

    public static ProductResponseListAll productResponseListAll(Product produto) {
        return new ProductResponseListAll(
                produto.getId(),
                produto.getName(),
                produto.getPrice(),
                produto.getDescription(),
                produto.getTypeProduct());
    }

}
