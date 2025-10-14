package erickwck.springsecurity_webToken.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder(toBuilder = true)
public record LoginDto(
        @NotBlank(message = "Campo obrigatorio")
        String username,
        @NotBlank(message = "Campo obrigatorio")
        String password
) {
}
