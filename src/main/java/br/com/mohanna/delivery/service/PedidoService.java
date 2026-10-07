package br.com.mohanna.delivery.service;

import br.com.mohanna.delivery.domain.cliente.Cliente;
import br.com.mohanna.delivery.domain.itemPedido.ItemPedido;
import br.com.mohanna.delivery.domain.pedido.Pedido;
import br.com.mohanna.delivery.domain.pedido.PedidoRequestDTO;
import br.com.mohanna.delivery.domain.pedido.PedidoResponseDTO;
import br.com.mohanna.delivery.domain.pedido.Status;
import br.com.mohanna.delivery.exception.NotFoundException;
import br.com.mohanna.delivery.repository.ClienteRepository;
import br.com.mohanna.delivery.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PedidoService {
    private final PedidoRepository repository;
    private final ClienteRepository clienteRepository;

    public void criarPedido(PedidoRequestDTO pedidoRequestDTO){
        Cliente cliente = clienteRepository.findById(pedidoRequestDTO.idCliente())
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));

        Pedido pedido = Pedido.builder()
                .cliente(cliente)
                .dataPedido(LocalDateTime.now())
                .valorTotal(BigDecimal.ZERO)
                .status(Status.PENDENTE)
                .build();

        repository.save(pedido);
    }

    public List<PedidoResponseDTO> listarTodos(){
        List<Pedido> pedidos = repository.findAll();

        return pedidos.stream()
                .map(p -> new PedidoResponseDTO(p.getId(), p.getCliente().getId(), p.getValorTotal(), p.getStatus()))
                .toList();
    }

    public PedidoResponseDTO listarPorId(Long id){
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado"));

        return new PedidoResponseDTO(pedido.getId(), pedido.getCliente().getId(), pedido.getValorTotal(), pedido.getStatus());
    }

    public void cancelarPorId(Long id){
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado"));
        pedido.setStatus(Status.CANCELADO);
        repository.save(pedido);
    }
}
