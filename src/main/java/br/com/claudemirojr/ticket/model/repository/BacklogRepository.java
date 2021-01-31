package br.com.claudemirojr.ticket.model.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.claudemirojr.ticket.model.entity.Backlog;
import br.com.claudemirojr.ticket.model.entity.Solicitacao;

public interface BacklogRepository extends JpaRepository<Backlog, Long> {

	Page<Backlog> findBySolicitacao(Solicitacao solicitacao, Pageable pageable);

	Optional<Backlog> findByIdAndSolicitacao(Long id, Solicitacao solicitacao);

}
