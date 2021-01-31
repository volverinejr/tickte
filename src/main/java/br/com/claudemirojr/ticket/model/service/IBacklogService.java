package br.com.claudemirojr.ticket.model.service;

import org.springframework.data.domain.Page;

import br.com.claudemirojr.ticket.model.ParamsRequestModel;
import br.com.claudemirojr.ticket.model.entity.Backlog;
import br.com.claudemirojr.ticket.model.entity.Solicitacao;

public interface IBacklogService {

	public Page<Backlog> findBySolicitacao(Solicitacao solicitacao, ParamsRequestModel prm);

	public Backlog FindById(Long id);

	public Backlog findByIdAndSolicitacao(Long id, Solicitacao solicitacao);

	public Backlog save(Backlog solicitacaoBacklog);

	public void deleteById(Long id);

}
