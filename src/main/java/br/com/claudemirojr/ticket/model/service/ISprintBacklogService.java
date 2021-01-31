package br.com.claudemirojr.ticket.model.service;

import java.util.List;

import br.com.claudemirojr.ticket.model.entity.Sprint;
import br.com.claudemirojr.ticket.model.entity.SprintBacklog;

public interface ISprintBacklogService {

	public List<SprintBacklog> findBySprint(Sprint sprint);

	public SprintBacklog FindById(Long id);

	public SprintBacklog findByIdAndSprint(Long id, Sprint sprint);

	public SprintBacklog save(SprintBacklog sprintSolicitacaoBacklog);

	public void deleteById(Long id);

}
