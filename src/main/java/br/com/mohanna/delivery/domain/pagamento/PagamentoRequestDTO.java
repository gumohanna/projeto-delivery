package br.com.mohanna.delivery.domain.pagamento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PagamentoRequestDTO(
        @NotNull @Positive
        Long idPedido,

        @NotBlank
        String metodoPagamento
) {
}
