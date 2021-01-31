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
import br.com.claudemirojr.ticket.model.entity.Pessoa;
import br.com.claudemirojr.ticket.model.entity.PessoaSolicitacao;
import br.com.claudemirojr.ticket.model.entity.Solicitacao;
import br.com.claudemirojr.ticket.model.entity.SolicitacaoMovimentacao;
import br.com.claudemirojr.ticket.model.entity.dto.IAnaliseBacklog;
import br.com.claudemirojr.ticket.model.entity.enums.SolicitacaoStatus;
import br.com.claudemirojr.ticket.model.repository.PessoaRepository;
import br.com.claudemirojr.ticket.model.repository.SolicitacaoRepository;
import br.com.claudemirojr.ticket.model.service.ISolicitacaoService;

@Service
public class SolicitacaoService implements ISolicitacaoService {

	@Autowired
	private SolicitacaoRepository _repository;

	@Autowired
	private PessoaRepository _repositoryPessoa;

	@Autowired
	private SolicitacaoMovimentacaoService _serviceSolicitacaoMovimentacao;

	@Autowired
	private PessoaSolicitacaoService _servicePessoaSolicitacao;

	@Override
	@Transactional(readOnly = true)
	public Page<Solicitacao> findAll(ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		return _repository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Solicitacao> findAllAnalise(ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		List<SolicitacaoStatus> solicitacaoStatus = new ArrayList<SolicitacaoStatus>();
		solicitacaoStatus.add(SolicitacaoStatus.CADASTRADA);
		solicitacaoStatus.add(SolicitacaoStatus.ANALISADA);

		return _repository.findByStatusAtualIn(solicitacaoStatus, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Solicitacao> findByIdGreaterThanEqual(Long id, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		return _repository.findByIdGreaterThanEqual(id, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Solicitacao> findByDescricaoContaining(String descricao, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		return _repository.findByDescricaoIgnoreCaseContaining(descricao, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Solicitacao FindById(Long id) {
		return _repository.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format("Solicitação não encontrada para id %d", id)));
	}

	@Override
	@Transactional(readOnly = false)
	public Solicitacao nova(Solicitacao solicitacao) {
		Pessoa pessoa = _repositoryPessoa.findById(solicitacao.getPessoa().getId()).get();

		Long resultado = _repositoryPessoa.findByPessoaTemSistema(pessoa.getId(), solicitacao.getSistema().getId());

		if (resultado == 0) {
			throw new NotFoundException(
					String.format("Conjunto Pessoa/Sistema não encontrada para cadastrar Solicitação"));
		}

		solicitacao.setStatusAtual(SolicitacaoStatus.CADASTRADA);
		Solicitacao novaSolicitacao = _repository.save(solicitacao);

		// ligando a solicitação a pessoa
		PessoaSolicitacao pessoaSolicitacao = new PessoaSolicitacao(null, pessoa, novaSolicitacao, null);
		_servicePessoaSolicitacao.save(pessoaSolicitacao);

		// gerando a movimentacao da solicitação
		SolicitacaoMovimentacao solicitacaoMovimentacao = new SolicitacaoMovimentacao(null, novaSolicitacao,
				SolicitacaoStatus.CADASTRADA, null);

		// atualizando a última movimentacao da solicitação
		solicitacaoMovimentacao = _serviceSolicitacaoMovimentacao.save(solicitacaoMovimentacao);

		return novaSolicitacao;
	}

	@Override
	@Transactional(readOnly = false)
	public Solicitacao update(Solicitacao solicitacao) {
		Pessoa pessoa = _repositoryPessoa.findById(solicitacao.getPessoa().getId()).get();

		Long resultado = _repositoryPessoa.findByPessoaTemSistema(pessoa.getId(), solicitacao.getSistema().getId());

		if (resultado == 0) {
			throw new NotFoundException(
					String.format("Conjunto Pessoa/Sistema não encontrada para cadastrar solicitação"));
		}

		if (solicitacao.getStatusAtual() != SolicitacaoStatus.CADASTRADA) {
			throw new NotFoundException(String.format("Solicitação, já movimentada, não pode mais ser modificada"));
		}

		return _repository.save(solicitacao);
	}

	@Override
	@Transactional(readOnly = true)
	public Solicitacao FindBySolicitacaoStatusAtualAnalisada(Long id) {
		List<SolicitacaoStatus> solicitacaoStatus = new ArrayList<SolicitacaoStatus>();
		solicitacaoStatus.add(SolicitacaoStatus.ANALISADA);

		return _repository.findByIdAndStatusAtualIn(id, solicitacaoStatus).orElseThrow(
				() -> new NotFoundException(String.format("Solicitação, em analise, não encontrada para id %d", id)));

	}

	@Override
	@Transactional(readOnly = true)
	public Page<Solicitacao> findByAnalisePrioridade(Integer prioridade, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		List<SolicitacaoStatus> solicitacaoStatus = new ArrayList<SolicitacaoStatus>();
		solicitacaoStatus.add(SolicitacaoStatus.ANALISADA);

		return _repository.findByStatusAtualInAndPrioridade(solicitacaoStatus, prioridade, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<IAnaliseBacklog> FindByTeste(Long idSprint, Integer prioridade, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		return _repository.findByTeste(prioridade, pageable);
	}

}
