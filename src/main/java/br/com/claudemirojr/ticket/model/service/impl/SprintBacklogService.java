package br.com.claudemirojr.ticket.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.claudemirojr.ticket.exception.NotFoundException;
import br.com.claudemirojr.ticket.model.entity.Sprint;
import br.com.claudemirojr.ticket.model.entity.SprintBacklog;
import br.com.claudemirojr.ticket.model.repository.SprintBacklogRepository;
import br.com.claudemirojr.ticket.model.service.ISprintBacklogService;

@Service
public class SprintBacklogService implements ISprintBacklogService {

	@Autowired
	private SprintBacklogRepository _repository;

	@Override
	@Transactional(readOnly = true)
	public List<SprintBacklog> findBySprint(Sprint sprint) {
		return _repository.findBySprint(sprint);
	}

	@Override
	@Transactional(readOnly = true)
	public SprintBacklog FindById(Long id) {
		return _repository.findById(id).orElseThrow(
				() -> new NotFoundException(String.format("SprintSolicitacaoBacklog não encontrado para id %d", id)));
	}

	@Override
	@Transactional(readOnly = true)
	public SprintBacklog findByIdAndSprint(Long id, Sprint sprint) {
		return _repository.findByIdAndSprint(id, sprint).orElseThrow(
				() -> new NotFoundException(String.format("Conjunto Sprint/SolicitacaoBacklog não encontrado")));
	}

	@Override
	@Transactional(readOnly = false)
	public SprintBacklog save(SprintBacklog sprintSolicitacaoBacklog) {
		return _repository.save(sprintSolicitacaoBacklog);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteById(Long id) {
		_repository.deleteById(id);
	}

}
