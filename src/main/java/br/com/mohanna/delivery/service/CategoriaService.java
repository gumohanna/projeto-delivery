package br.com.mohanna.delivery.service;

import br.com.mohanna.delivery.domain.categoria.Categoria;
import br.com.mohanna.delivery.domain.categoria.CategoriaDTO;
import br.com.mohanna.delivery.exception.NotFoundException;
import br.com.mohanna.delivery.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {
    private final CategoriaRepository repository;

    public void criarCategoria(CategoriaDTO categoriaDTO){
        Categoria categoria = Categoria.builder()
                .nome(categoriaDTO.nome())
                .build();
        repository.save(categoria);
    }

    public List<CategoriaDTO> listarTodas(){
        List<Categoria> categorias = repository.findAll();
        return categorias.stream()
                .map(c -> new CategoriaDTO(c.getId(), c.getNome()))
                .toList();
    }

    public CategoriaDTO listarPorId(Long id){
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada"));
        return new CategoriaDTO(categoria.getId(), categoria.getNome());
    }

    public void atualizarPorId(Long id, CategoriaDTO categoriaDTO){
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada"));

        categoria.setNome(categoriaDTO.nome());
        repository.save(categoria);
    }

    public void deletarPorId(Long id){
        repository.deleteById(id);
    }
}
