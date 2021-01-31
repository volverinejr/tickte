package br.com.claudemirojr.ticket.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.claudemirojr.ticket.model.entity.Pessoa;
import br.com.claudemirojr.ticket.model.entity.PessoaSolicitacao;
import br.com.claudemirojr.ticket.model.entity.Solicitacao;

public interface PessoaSolicitacaoRepository extends JpaRepository<PessoaSolicitacao, Long> {

	List<PessoaSolicitacao> findByPessoa(Pessoa pessoa);

	Optional<PessoaSolicitacao> findByPessoaAndSolicitacao(Pessoa pessoa, Solicitacao solicitacao);

}
