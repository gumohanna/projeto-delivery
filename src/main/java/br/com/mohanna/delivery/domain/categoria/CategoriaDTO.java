package br.com.mohanna.delivery.domain.categoria;

import jakarta.validation.constraints.NotBlank;

public record CategoriaDTO(
        Long id,

        @NotBlank(message = "Nome é obrigatório")
        String nome
) {
}
