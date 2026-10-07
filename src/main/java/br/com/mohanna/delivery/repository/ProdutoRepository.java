package br.com.mohanna.delivery.repository;

import br.com.mohanna.delivery.domain.produto.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
