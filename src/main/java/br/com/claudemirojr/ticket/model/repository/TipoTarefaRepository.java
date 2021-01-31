package br.com.claudemirojr.ticket.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.claudemirojr.ticket.model.entity.TipoTarefa;

public interface TipoTarefaRepository extends JpaRepository<TipoTarefa, Long> {

	Page<TipoTarefa> findByIdGreaterThanEqual(Long id, Pageable pageable);

	Page<TipoTarefa> findByNomeIgnoreCaseContaining(String nome, Pageable pageable);

	Page<TipoTarefa> findByAtivo(Boolean ativo, Pageable pageable);

}
