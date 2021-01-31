package br.com.claudemirojr.ticket.model.service;

import org.springframework.data.domain.Page;

import br.com.claudemirojr.ticket.model.ParamsRequestModel;
import br.com.claudemirojr.ticket.model.entity.Solicitacao;

public interface IAnaliseService {
	
	public Page<Solicitacao> findAll(ParamsRequestModel prm);
	
	public Solicitacao findById(Long id);

	public Page<Solicitacao> findByIdGreaterThanEqual(Long id, ParamsRequestModel prm);

	public Page<Solicitacao> findByDescricaoContaining(String descricao, ParamsRequestModel prm);
	
	public Solicitacao analise(Solicitacao solicitacao);

}
