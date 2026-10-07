package br.com.mohanna.delivery.controller;

import br.com.mohanna.delivery.domain.categoria.CategoriaDTO;
import br.com.mohanna.delivery.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
@Validated
public class CategoriaController {
    private final CategoriaService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criarCategoria(@RequestBody @Valid CategoriaDTO categoriaDTO){
        service.criarCategoria(categoriaDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoriaDTO> listarTodos(){
        return service.listarTodas();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoriaDTO listarPorId(@PathVariable Long id){
        return service.listarPorId(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void atualizarPorId(@PathVariable Long id,
                               @RequestBody @Valid CategoriaDTO categoriaDTO){
        service.atualizarPorId(id, categoriaDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarPorId(@PathVariable Long id){
        service.deletarPorId(id);
    }
}
