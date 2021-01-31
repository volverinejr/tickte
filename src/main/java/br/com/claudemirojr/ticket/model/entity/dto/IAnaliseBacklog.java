package br.com.claudemirojr.ticket.model.entity.dto;

public interface IAnaliseBacklog {

	Long getSolicitacaoId();

	String getSolicitacaoDescricao();

	String getPessoaNome();

	String getSistemaNome();

	Long getBacklogId();

	String getBacklogDescricao();
	
	Boolean getCadastrado();
	
	Long getSprintCadastrado();
	
	Long getSprintBacklogId();
}
