package erickwck.springsecurity_webToken.controller.dto;

import erickwck.springsecurity_webToken.entity.TypeProduct;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRequest(

        @NotBlank(message = "Insira o nome do produto.")
        String name,
        @NotNull(message = "Insira o preço do produto.")
        BigDecimal price,
        String description,

        @NotNull(message = "Insira o tipo exemplo: Eletrodomestico, TECNOLOGIA, COZINHA")
        TypeProduct typeProduct
) {
}
