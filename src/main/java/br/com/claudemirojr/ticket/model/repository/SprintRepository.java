package br.com.claudemirojr.ticket.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.claudemirojr.ticket.model.entity.Sprint;

public interface SprintRepository extends JpaRepository<Sprint, Long> {

	Page<Sprint> findByIdGreaterThanEqual(Long id, Pageable pageable);

	Page<Sprint> findByNomeIgnoreCaseContaining(String nome, Pageable pageable);

}
