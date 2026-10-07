package br.com.mohanna.delivery.domain.itemPedido;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ItemPedidoRequestDTO(
        @NotNull @Positive
        Long idPedido,

        @NotNull @Positive
        Long idProduto,

        @NotNull @Positive
        Integer quantidade
) {
}
