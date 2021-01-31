package br.com.claudemirojr.ticket.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.claudemirojr.ticket.model.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	Page<Cliente> findByIdGreaterThanEqual(Long id, Pageable pageable);

	Page<Cliente> findByNomeIgnoreCaseContaining(String nome, Pageable pageable);

	Page<Cliente> findByAtivo(Boolean ativo, Pageable pageable);

}
