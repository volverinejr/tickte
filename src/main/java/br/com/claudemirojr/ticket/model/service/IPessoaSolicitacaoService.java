package br.com.claudemirojr.ticket.model.service;

import java.util.List;

import br.com.claudemirojr.ticket.model.entity.Pessoa;
import br.com.claudemirojr.ticket.model.entity.PessoaSolicitacao;
import br.com.claudemirojr.ticket.model.entity.Solicitacao;

public interface IPessoaSolicitacaoService {

	public List<PessoaSolicitacao> findByPessoa(Pessoa pessoa);

	public PessoaSolicitacao FindById(Long id);

	public PessoaSolicitacao findByPessoaAndSolicitacao(Pessoa pessoa, Solicitacao solicitacao);

	public PessoaSolicitacao save(PessoaSolicitacao pessoaSolicitacao);

	public void deleteById(Long id);

}
