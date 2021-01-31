package br.com.claudemirojr.ticket.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.claudemirojr.ticket.exception.NotFoundException;
import br.com.claudemirojr.ticket.model.ParamsRequestModel;
import br.com.claudemirojr.ticket.model.entity.Backlog;
import br.com.claudemirojr.ticket.model.entity.Solicitacao;
import br.com.claudemirojr.ticket.model.repository.BacklogRepository;
import br.com.claudemirojr.ticket.model.service.IBacklogService;

@Service
public class BacklogService implements IBacklogService {

	@Autowired
	private BacklogRepository _repository;

	@Override
	@Transactional(readOnly = true)
	public Page<Backlog> findBySolicitacao(Solicitacao solicitacao, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();
		
		return _repository.findBySolicitacao(solicitacao, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Backlog FindById(Long id) {
		return _repository.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format("Backlog não encontrado para id %d", id)));
	}

	@Override
	@Transactional(readOnly = true)
	public Backlog findByIdAndSolicitacao(Long id, Solicitacao solicitacao) {
		return _repository.findByIdAndSolicitacao(id, solicitacao)
				.orElseThrow(() -> new NotFoundException(String.format("Conjunto Solicitacao/Backlog não encontrado")));
	}

	@Override
	@Transactional(readOnly = false)
	public Backlog save(Backlog solicitacaoBacklog) {
		return _repository.save(solicitacaoBacklog);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteById(Long id) {
		_repository.deleteById(id);
	}

}
