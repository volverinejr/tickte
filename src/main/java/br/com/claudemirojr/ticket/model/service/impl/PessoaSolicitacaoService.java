package br.com.claudemirojr.ticket.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.claudemirojr.ticket.exception.NotFoundException;
import br.com.claudemirojr.ticket.model.entity.Pessoa;
import br.com.claudemirojr.ticket.model.entity.PessoaSolicitacao;
import br.com.claudemirojr.ticket.model.entity.Solicitacao;
import br.com.claudemirojr.ticket.model.repository.PessoaSolicitacaoRepository;
import br.com.claudemirojr.ticket.model.service.IPessoaSolicitacaoService;

@Service
public class PessoaSolicitacaoService implements IPessoaSolicitacaoService {

	@Autowired
	private PessoaSolicitacaoRepository _repository;

	@Override
	@Transactional(readOnly = true)
	public List<PessoaSolicitacao> findByPessoa(Pessoa pessoa) {
		return _repository.findByPessoa(pessoa);
	}

	@Override
	@Transactional(readOnly = true)
	public PessoaSolicitacao FindById(Long id) {
		return _repository.findById(id).orElseThrow(
				() -> new NotFoundException(String.format("PessoaSolicitacao não encontrado para id %d", id)));
	}

	@Override
	@Transactional(readOnly = true)
	public PessoaSolicitacao findByPessoaAndSolicitacao(Pessoa pessoa, Solicitacao solicitacao) {
		return _repository.findByPessoaAndSolicitacao(pessoa, solicitacao)
				.orElseThrow(() -> new NotFoundException(String.format("Conjunto Pessoa/Solicitacao não encontrado")));
	}

	@Override
	@Transactional(readOnly = false)
	public PessoaSolicitacao save(PessoaSolicitacao pessoaSolicitacao) {
		return _repository.save(pessoaSolicitacao);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteById(Long id) {
		_repository.deleteById(id);
	}

}
