package br.com.claudemirojr.ticket.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.claudemirojr.ticket.model.entity.Time;

public interface TimeRepository extends JpaRepository<Time, Long> {

	Page<Time> findByIdGreaterThanEqual(Long id, Pageable pageable);

	Page<Time> findByNomeIgnoreCaseContaining(String nome, Pageable pageable);

	Page<Time> findByAtivo(Boolean ativo, Pageable pageable);

}
