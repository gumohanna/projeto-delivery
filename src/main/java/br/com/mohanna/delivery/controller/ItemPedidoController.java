package br.com.mohanna.delivery.controller;

import br.com.mohanna.delivery.domain.itemPedido.ItemPedidoRequestDTO;
import br.com.mohanna.delivery.domain.itemPedido.ItemPedidoResponseDTO;
import br.com.mohanna.delivery.service.ItemPedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itenspedido")
@RequiredArgsConstructor
@Validated
public class ItemPedidoController {
    private final ItemPedidoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criarItemPedido(@RequestBody @Valid ItemPedidoRequestDTO itemPedidoRequestDTO){
        service.criarItemPedido(itemPedidoRequestDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ItemPedidoResponseDTO> listarTodos(){
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ItemPedidoResponseDTO listarPorId(@PathVariable Long id){
        return service.listarPorId(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void atualizarPorId(@PathVariable Long id,
                               @RequestBody @Valid ItemPedidoRequestDTO itemPedidoRequestDTO){
        service.atualizarPorId(id, itemPedidoRequestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarPorId(@PathVariable Long id){
        service.deletarPorId(id);
    }
}
