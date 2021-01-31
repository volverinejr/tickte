package br.com.claudemirojr.ticket.model.service;

import org.springframework.data.domain.Page;

import br.com.claudemirojr.ticket.model.ParamsRequestModel;
import br.com.claudemirojr.ticket.model.entity.Solicitacao;
import br.com.claudemirojr.ticket.model.entity.dto.IAnaliseBacklog;

public interface ISolicitacaoService {

	public Page<Solicitacao> findAll(ParamsRequestModel prm);

	public Page<Solicitacao> findByIdGreaterThanEqual(Long id, ParamsRequestModel prm);

	public Page<Solicitacao> findByDescricaoContaining(String descricao, ParamsRequestModel prm);

	public Page<Solicitacao> findAllAnalise(ParamsRequestModel prm);

	/*
	 * public Page<Solicitacao> findByStatusAtualIn(List<SolicitacaoStatus>
	 * solicitacaoStatus, ParamsRequestModel prm);
	 * 
	 * public Page<Solicitacao>
	 * findByStatusAtualInAndIdGreaterThanEqual(List<SolicitacaoStatus>
	 * solicitacaoStatus, Long id, ParamsRequestModel prm);
	 * 
	 * public Page<Solicitacao>
	 * findByStatusAtualInAndDescricaoIgnoreCaseContaining(List<SolicitacaoStatus>
	 * solicitacaoStatus, String descricao, ParamsRequestModel prm);
	 */

	public Solicitacao FindById(Long id);

	public Solicitacao FindBySolicitacaoStatusAtualAnalisada(Long id);

	public Solicitacao nova(Solicitacao solicitacao);

	public Solicitacao update(Solicitacao solicitacao);

	public Page<Solicitacao> findByAnalisePrioridade(Integer prioridade, ParamsRequestModel prm);

	public Page<IAnaliseBacklog> FindByTeste(Long idSprint, Integer prioridade, ParamsRequestModel prm);

}
