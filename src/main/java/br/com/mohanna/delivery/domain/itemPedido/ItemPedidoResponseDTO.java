package br.com.mohanna.delivery.domain.itemPedido;

import java.math.BigDecimal;

public record ItemPedidoResponseDTO(Long id, Long idPedido, Long idProduto, Integer quantidae, BigDecimal precoUnitario) {
}
