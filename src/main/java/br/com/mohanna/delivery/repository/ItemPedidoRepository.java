package br.com.mohanna.delivery.repository;

import br.com.mohanna.delivery.domain.itemPedido.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}
