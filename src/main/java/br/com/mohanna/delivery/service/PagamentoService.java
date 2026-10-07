package br.com.mohanna.delivery.service;

import br.com.mohanna.delivery.domain.pagamento.Pagamento;
import br.com.mohanna.delivery.domain.pagamento.PagamentoRequestDTO;
import br.com.mohanna.delivery.domain.pagamento.PagamentoResponseDTO;
import br.com.mohanna.delivery.domain.pagamento.StatusPagamento;
import br.com.mohanna.delivery.domain.pedido.Pedido;
import br.com.mohanna.delivery.exception.NotFoundException;
import br.com.mohanna.delivery.repository.PagamentoRepository;
import br.com.mohanna.delivery.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PagamentoService {
    private final PagamentoRepository repository;
    private final PedidoRepository pedidoRepository;

    public void criarPagamento(PagamentoRequestDTO pagamentoRequestDTO){
        Pedido pedido = pedidoRepository.findById(pagamentoRequestDTO.idPedido())
                .orElseThrow(() -> new NotFoundException("Esse pedido não existe"));

        Pagamento pagamento = Pagamento.builder()
                .pedido(pedido)
                .valor(pedido.getValorTotal())
                .statusPagamento(StatusPagamento.PENDENTE)
                .metodoPagamento(pagamentoRequestDTO.metodoPagamento())
                .build();
        repository.save(pagamento);
    }

    public List<PagamentoResponseDTO> listarTodos(){
        List<Pagamento> pagamentos = repository.findAll();

        return pagamentos.stream()
                .map(p -> new PagamentoResponseDTO(p.getId(), p.getPedido().getId(), p.getValor(), p.getStatusPagamento(), p.getMetodoPagamento()))
                .toList();
    }

    public PagamentoResponseDTO listarPorId(Long id){
        Pagamento pagamento = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pagamento não encontrado"));
        return new PagamentoResponseDTO(pagamento.getId(), pagamento.getPedido().getId(), pagamento.getValor(), pagamento.getStatusPagamento(), pagamento.getMetodoPagamento());
    }

    public void cancelarPagamento(Long id){
        Pagamento pagamento = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pagamento não encontrado"));
        pagamento.setStatusPagamento(StatusPagamento.CANCELADO);
        repository.save(pagamento);
    }

    public void pagar(Long id){
        Pagamento pagamento = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pagamento não encontrado"));
        pagamento.setStatusPagamento(StatusPagamento.PAGO);
        repository.save(pagamento);
    }
}
