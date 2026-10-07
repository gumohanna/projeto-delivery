package br.com.mohanna.delivery.controller;

import br.com.mohanna.delivery.domain.pedido.PedidoRequestDTO;
import br.com.mohanna.delivery.domain.pedido.PedidoResponseDTO;
import br.com.mohanna.delivery.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
@Validated
public class PedidoController {
    private final PedidoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criarPedido(@RequestBody @Valid PedidoRequestDTO pedidoRequestDTO){
        service.criarPedido(pedidoRequestDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PedidoResponseDTO> listarTodos(){
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PedidoResponseDTO listarPorId(@PathVariable Long id){
        return service.listarPorId(id);
    }

    @PutMapping("/{id}/cancelar")
    @ResponseStatus(HttpStatus.OK)
    public void cancelarPorId(@PathVariable Long id){
        service.cancelarPorId(id);
    }
}
