package br.com.claudemirojr.ticket.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.claudemirojr.ticket.model.ParamsRequestModel;
import br.com.claudemirojr.ticket.model.entity.Solicitacao;
import br.com.claudemirojr.ticket.model.entity.SolicitacaoMovimentacao;
import br.com.claudemirojr.ticket.model.repository.SolicitacaoMovimentacaoRepository;
import br.com.claudemirojr.ticket.model.service.ISolicitacaoMovimentacaoService;

@Service
public class SolicitacaoMovimentacaoService implements ISolicitacaoMovimentacaoService {

	@Autowired
	private SolicitacaoMovimentacaoRepository _repository;

	@Override
	@Transactional(readOnly = true)
	public Page<SolicitacaoMovimentacao> findAll(ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		return _repository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<SolicitacaoMovimentacao> findBySolicitacao(Solicitacao solicitacao) {
		return _repository.findBySolicitacao(solicitacao);
	}

	@Override
	@Transactional(readOnly = false)
	public SolicitacaoMovimentacao save(SolicitacaoMovimentacao solicitacaoMovimentacao) {
		return _repository.save(solicitacaoMovimentacao);
	}

}
