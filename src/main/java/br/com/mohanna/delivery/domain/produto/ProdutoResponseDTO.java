package br.com.mohanna.delivery.domain.produto;

import java.math.BigDecimal;

public record ProdutoResponseDTO(Long id, String nome, String descricao, BigDecimal preco) {
}
