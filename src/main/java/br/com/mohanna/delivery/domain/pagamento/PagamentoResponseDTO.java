package br.com.mohanna.delivery.domain.pagamento;

import br.com.mohanna.delivery.domain.pedido.Status;

import java.math.BigDecimal;

public record PagamentoResponseDTO(Long id, Long idPedido, BigDecimal valor, StatusPagamento status, String metodoPagamento) {
}
