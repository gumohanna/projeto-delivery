package br.com.mohanna.delivery.service;

import br.com.mohanna.delivery.domain.itemPedido.ItemPedido;
import br.com.mohanna.delivery.domain.itemPedido.ItemPedidoRequestDTO;
import br.com.mohanna.delivery.domain.itemPedido.ItemPedidoResponseDTO;
import br.com.mohanna.delivery.domain.pedido.Pedido;
import br.com.mohanna.delivery.domain.produto.Produto;
import br.com.mohanna.delivery.exception.NotFoundException;
import br.com.mohanna.delivery.repository.ItemPedidoRepository;
import br.com.mohanna.delivery.repository.PedidoRepository;
import br.com.mohanna.delivery.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemPedidoService {
    private final ItemPedidoRepository repository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    public void criarItemPedido(ItemPedidoRequestDTO itemPedidoRequestDTO){
        Pedido pedido = pedidoRepository.findById(itemPedidoRequestDTO.idPedido())
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado"));

        Produto produto = produtoRepository.findById(itemPedidoRequestDTO.idProduto())
                .orElseThrow(() -> new NotFoundException("Produto não encontrado"));

        ItemPedido itemPedido = ItemPedido.builder()
                .pedido(pedido)
                .produto(produto)
                .quantidade(itemPedidoRequestDTO.quantidade())
                .precoUnitario(produto.getPreco())
                .build();

        repository.save(itemPedido);

        BigDecimal subtotal = produto.getPreco()
                .multiply(BigDecimal.valueOf(itemPedidoRequestDTO.quantidade()));
        pedido.setValorTotal(pedido.getValorTotal().add(subtotal));
        pedidoRepository.save(pedido);
    }

    public List<ItemPedidoResponseDTO> listarTodos(){
        List<ItemPedido> itensPedido = repository.findAll();


        return itensPedido.stream()
                .map(i -> new ItemPedidoResponseDTO(i.getId(), i.getPedido().getId(), i.getProduto().getId(),
                        i.getQuantidade(), i.getPrecoUnitario()))
                .toList();
    }

    public ItemPedidoResponseDTO listarPorId(Long id){
        ItemPedido itemPedido = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("ItemPedido não encontrado"));

        return new ItemPedidoResponseDTO(itemPedido.getId(), itemPedido.getPedido().getId(), itemPedido.getProduto().getId(),
                itemPedido.getQuantidade(), itemPedido.getPrecoUnitario());
    }

    public void atualizarPorId(Long id, ItemPedidoRequestDTO itemPedidoRequestDTO){
        ItemPedido itemPedido = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("ItemPedido não encontrado"));

        Pedido pedido = pedidoRepository.findById(itemPedidoRequestDTO.idPedido())
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado"));

        Produto produto = produtoRepository.findById(itemPedidoRequestDTO.idProduto())
                .orElseThrow(() -> new NotFoundException("Produto não encontrado"));

        BigDecimal subtotal = produto.getPreco()
                .multiply(BigDecimal.valueOf(itemPedidoRequestDTO.quantidade()));

        itemPedido.setPedido(pedido);
        itemPedido.setProduto(produto);
        itemPedido.setQuantidade(itemPedidoRequestDTO.quantidade());
        itemPedido.setPrecoUnitario(produto.getPreco());
        pedido.setValorTotal(pedido.getValorTotal().add(subtotal));
        repository.save(itemPedido);
    }

    public void deletarPorId(Long id){
        ItemPedido itemPedido = repository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Item não encontrado"));
        itemPedido.getPedido().setValorTotal(BigDecimal.ZERO);
        repository.deleteById(id);
    }
}
