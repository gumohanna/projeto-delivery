package br.com.mohanna.delivery.controller;

import br.com.mohanna.delivery.domain.cliente.ClienteRequestDTO;
import br.com.mohanna.delivery.domain.cliente.ClienteResponseDTO;
import br.com.mohanna.delivery.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
@Validated
public class ClienteController {
    private final ClienteService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criarCliente(@Valid @RequestBody ClienteRequestDTO clienteRequestDTO){
        service.criarCliente(clienteRequestDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ClienteResponseDTO> listarTodos(){
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClienteResponseDTO listarPorId(@PathVariable Long id){
        return service.listarPorId(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void atualizarPorId(@PathVariable Long id,
                               @RequestBody @Valid ClienteRequestDTO clienteRequestDTO){
        service.atualizarPorId(id, clienteRequestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarPorId(@PathVariable Long id){
        service.deletarPorId(id);
    }
}
