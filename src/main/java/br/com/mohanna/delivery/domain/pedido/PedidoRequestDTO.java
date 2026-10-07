package br.com.mohanna.delivery.domain.pedido;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PedidoRequestDTO(
        @NotNull @Positive
        Long idCliente
        ) {
}
