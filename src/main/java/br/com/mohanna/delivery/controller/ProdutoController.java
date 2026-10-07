package br.com.mohanna.delivery.controller;

import br.com.mohanna.delivery.domain.produto.ProdutoRequestDTO;
import br.com.mohanna.delivery.domain.produto.ProdutoResponseDTO;
import br.com.mohanna.delivery.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
@Validated
public class ProdutoController {
    private final ProdutoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criarProduto(@RequestBody @Valid ProdutoRequestDTO produtoRequestDTO){
        service.criarProduto(produtoRequestDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProdutoResponseDTO> listarTodos(){
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProdutoResponseDTO listarPorId(@PathVariable Long id){
        return service.listarPorId(id);
    }

    @GetMapping("/page")
    public Page<ProdutoResponseDTO> findAll(Pageable pageable) {
        return service.paginasProduto(pageable);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void atualizarPorId(@PathVariable Long id,
                               @RequestBody @Valid ProdutoRequestDTO produtoRequestDTO){
        service.atualizarPorId(id, produtoRequestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarPorId(@PathVariable Long id){
        service.deletarPorId(id);
    }
}
