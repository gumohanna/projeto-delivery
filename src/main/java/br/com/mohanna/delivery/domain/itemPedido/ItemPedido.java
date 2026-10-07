package br.com.mohanna.delivery.domain.itemPedido;

import br.com.mohanna.delivery.domain.pedido.Pedido;
import br.com.mohanna.delivery.domain.produto.Produto;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "itens_pedido")
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "id_pedido")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(nullable = false, name = "id_produto")
    private Produto produto;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(name = "preco_unit", nullable = false)
    private BigDecimal precoUnitario;
}
