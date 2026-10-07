package br.com.mohanna.delivery.domain.pedido;

import br.com.mohanna.delivery.domain.cliente.Cliente;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false ,name = "id_cliente")
    private Cliente cliente;

    @Column(nullable = false, name = "data_pedido")
    private LocalDateTime dataPedido;

    @Column(nullable = false, name = "valor_total")
    private BigDecimal valorTotal;

    @Column(nullable = false)
    private Status status;
}