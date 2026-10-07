package br.com.mohanna.delivery.controller;

import br.com.mohanna.delivery.domain.pagamento.PagamentoRequestDTO;
import br.com.mohanna.delivery.domain.pagamento.PagamentoResponseDTO;
import br.com.mohanna.delivery.service.PagamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagamentos")
@RequiredArgsConstructor
@Validated
public class PagamentoController {
    private final PagamentoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criarPagamento(@RequestBody @Valid PagamentoRequestDTO pagamentoRequestDTO){
        service.criarPagamento(pagamentoRequestDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PagamentoResponseDTO> listarTodos(){
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PagamentoResponseDTO listarPorId(@PathVariable Long id){
        return service.listarPorId(id);
    }

    @PutMapping("/{id}/cancelar")
    @ResponseStatus(HttpStatus.OK)
    public void cancelarPagamento(@PathVariable Long id){
        service.cancelarPagamento(id);
    }

    @PutMapping("/{id}/pagar")
    @ResponseStatus(HttpStatus.OK)
    public void pagar(@PathVariable Long id){
        service.pagar(id);
    }
}
