package br.com.mohanna.delivery.domain.pagamento;

import br.com.mohanna.delivery.domain.pedido.Pedido;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "pagamentos")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(nullable = false, name = "id_pedido")
    private Pedido pedido;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false, name = "status")
    private StatusPagamento statusPagamento;

    @Column(nullable = false, name = "metodo_pgt")
    private String metodoPagamento;
}
