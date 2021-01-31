package br.com.claudemirojr.ticket.model.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.claudemirojr.ticket.exception.NotFoundException;
import br.com.claudemirojr.ticket.model.ParamsRequestModel;
import br.com.claudemirojr.ticket.model.entity.Solicitacao;
import br.com.claudemirojr.ticket.model.entity.SolicitacaoMovimentacao;
import br.com.claudemirojr.ticket.model.entity.enums.SolicitacaoStatus;
import br.com.claudemirojr.ticket.model.repository.SolicitacaoRepository;
import br.com.claudemirojr.ticket.model.service.IAnaliseService;


@Service
public class AnaliseService implements IAnaliseService {

	@Autowired
	private SolicitacaoRepository _repository;
	
	@Autowired
	private SolicitacaoMovimentacaoService _serviceSolicitacaoMovimentacao;
	

	@Override
	@Transactional(readOnly = false)
	public Solicitacao analise(Solicitacao solicitacao) {
		Solicitacao analiseSolicitacao = _repository.save(solicitacao);

		// gerando a movimentacao da solicitação
		SolicitacaoMovimentacao solicitacaoMovimentacao = new SolicitacaoMovimentacao(null, analiseSolicitacao,
				analiseSolicitacao.getStatusAtual(), null);

		// atualizando a última movimentacao da solicitação
		solicitacaoMovimentacao = _serviceSolicitacaoMovimentacao.save(solicitacaoMovimentacao);

		return analiseSolicitacao;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Solicitacao> findAll(ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		List<SolicitacaoStatus> solicitacaoStatus = new ArrayList<SolicitacaoStatus>();
		solicitacaoStatus.add(SolicitacaoStatus.CADASTRADA);
		solicitacaoStatus.add(SolicitacaoStatus.ANALISADA);

		return _repository.findByStatusAtualIn(solicitacaoStatus, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Solicitacao findById(Long id) {
		List<SolicitacaoStatus> solicitacaoStatus = new ArrayList<SolicitacaoStatus>();
		solicitacaoStatus.add(SolicitacaoStatus.CADASTRADA);
		solicitacaoStatus.add(SolicitacaoStatus.ANALISADA);

		return _repository.findByIdAndStatusAtualIn(id, solicitacaoStatus).orElseThrow(
				() -> new NotFoundException(String.format("Solicitação, em análise, não encontrada para id %d", id)));

	}

	@Override
	@Transactional(readOnly = true)
	public Page<Solicitacao> findByIdGreaterThanEqual(Long id, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		List<SolicitacaoStatus> solicitacaoStatus = new ArrayList<SolicitacaoStatus>();
		solicitacaoStatus.add(SolicitacaoStatus.CADASTRADA);
		solicitacaoStatus.add(SolicitacaoStatus.ANALISADA);

		return _repository.findByStatusAtualInAndIdGreaterThanEqual(solicitacaoStatus, id, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Solicitacao> findByDescricaoContaining(String descricao, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		List<SolicitacaoStatus> solicitacaoStatus = new ArrayList<SolicitacaoStatus>();
		solicitacaoStatus.add(SolicitacaoStatus.CADASTRADA);
		solicitacaoStatus.add(SolicitacaoStatus.ANALISADA);

		return _repository.findByStatusAtualInAndDescricaoIgnoreCaseContaining(solicitacaoStatus, descricao, pageable);
	}

}
