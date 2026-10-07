package br.com.mohanna.delivery.domain.produto;

import java.math.BigDecimal;

public record ProdutoRequestDTO(Long idCategoria, String nome, String descricao, BigDecimal preco) {
}
