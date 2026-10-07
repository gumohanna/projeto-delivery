package br.com.mohanna.delivery.repository;

import br.com.mohanna.delivery.domain.pedido.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
