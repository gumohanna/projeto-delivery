package br.com.mohanna.delivery.service;

import br.com.mohanna.delivery.domain.categoria.Categoria;
import br.com.mohanna.delivery.domain.produto.Produto;
import br.com.mohanna.delivery.domain.produto.ProdutoRequestDTO;
import br.com.mohanna.delivery.domain.produto.ProdutoResponseDTO;
import br.com.mohanna.delivery.exception.NotFoundException;
import br.com.mohanna.delivery.repository.CategoriaRepository;
import br.com.mohanna.delivery.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRepository repository;
    private final CategoriaRepository categoriaRepository;

    public void criarProduto(ProdutoRequestDTO produtoRequestDTO){
        Categoria categoria = categoriaRepository.findById(produtoRequestDTO.idCategoria())
                .orElseThrow(() -> new NotFoundException("Essa categoria não existe."));

        Produto produto = Produto.builder()
                .categoria(categoria)
                .nome(produtoRequestDTO.nome())
                .descricao(produtoRequestDTO.descricao())
                .preco(produtoRequestDTO.preco())
                .build();
        repository.save(produto);
    }

    public List<ProdutoResponseDTO> listarTodos(){
        List<Produto> produtos = repository.findAll();

        return produtos.stream()
                .map(p -> new ProdutoResponseDTO(p.getId(), p.getNome(), p.getDescricao(), p.getPreco()))
                .toList();
    }

    public ProdutoResponseDTO listarPorId(Long id){
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado"));

        return new ProdutoResponseDTO(produto.getId(), produto.getNome(), produto.getDescricao(), produto.getPreco());
    }

    public void atualizarPorId(Long id, ProdutoRequestDTO produtoRequestDTO){
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado"));

        Categoria categoria = categoriaRepository.findById(produtoRequestDTO.idCategoria())
                        .orElseThrow(() -> new NotFoundException("Categoria não encontrada"));


        produto.setNome(produtoRequestDTO.nome());
        produto.setCategoria(categoria);
        produto.setDescricao(produtoRequestDTO.descricao());
        produto.setPreco(produtoRequestDTO.preco());
        repository.save(produto);
    }

    public Page<ProdutoResponseDTO> paginasProduto(Pageable pageable){
        return repository.findAll(pageable)
                .map(p -> new ProdutoResponseDTO(p.getId(), p.getNome(), p.getDescricao(), p.getPreco()));
    }

    public void deletarPorId(Long id){
        repository.deleteById(id);
    }
}
