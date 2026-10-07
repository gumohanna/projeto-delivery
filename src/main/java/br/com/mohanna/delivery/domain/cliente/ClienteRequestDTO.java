package br.com.mohanna.delivery.domain.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClienteRequestDTO(
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @Email @NotBlank(message = "E-mail é obrigatório")
        String email,

        @NotBlank(message = "Telefone é obrigatório")
        String telefone,

        @NotBlank(message = "Endereço é obrigatório")
        String endereco
) {
}
