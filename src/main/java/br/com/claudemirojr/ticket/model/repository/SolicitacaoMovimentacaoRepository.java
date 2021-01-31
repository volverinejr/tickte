package br.com.claudemirojr.ticket.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.claudemirojr.ticket.model.entity.Solicitacao;
import br.com.claudemirojr.ticket.model.entity.SolicitacaoMovimentacao;

public interface SolicitacaoMovimentacaoRepository extends JpaRepository<SolicitacaoMovimentacao, Long> {

	List<SolicitacaoMovimentacao> findBySolicitacao(Solicitacao solicitacao);

}
