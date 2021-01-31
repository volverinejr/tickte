 package br.com.claudemirojr.ticket.model.service;

import java.util.List;

import org.springframework.data.domain.Page;

import br.com.claudemirojr.ticket.model.ParamsRequestModel;
import br.com.claudemirojr.ticket.model.entity.Solicitacao;
import br.com.claudemirojr.ticket.model.entity.SolicitacaoMovimentacao;

public interface ISolicitacaoMovimentacaoService {

	public Page<SolicitacaoMovimentacao> findAll(ParamsRequestModel prm);

	public SolicitacaoMovimentacao save(SolicitacaoMovimentacao solicitacaoMovimentacao);
	
	public List<SolicitacaoMovimentacao> findBySolicitacao(Solicitacao solicitacao);

}
