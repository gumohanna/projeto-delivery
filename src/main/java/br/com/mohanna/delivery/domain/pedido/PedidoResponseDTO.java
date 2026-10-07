package br.com.mohanna.delivery.domain.pedido;

import java.math.BigDecimal;

public record PedidoResponseDTO(Long id, Long idCliente, BigDecimal valor, Status status) {
}
