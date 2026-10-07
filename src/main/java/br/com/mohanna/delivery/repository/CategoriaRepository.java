package br.com.mohanna.delivery.repository;

import br.com.mohanna.delivery.domain.categoria.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
