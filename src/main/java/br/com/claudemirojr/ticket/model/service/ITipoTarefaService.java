package br.com.claudemirojr.ticket.model.service;

import org.springframework.data.domain.Page;

import br.com.claudemirojr.ticket.model.ParamsRequestModel;
import br.com.claudemirojr.ticket.model.entity.TipoTarefa;

public interface ITipoTarefaService {

	public Page<TipoTarefa> findAll(ParamsRequestModel prm);

	public Page<TipoTarefa> findByIdGreaterThanEqual(Long id, ParamsRequestModel prm);

	public Page<TipoTarefa> findByNomeContaining(String nome, ParamsRequestModel prm);

	public Page<TipoTarefa> findByAtivo(Boolean ativo, ParamsRequestModel prm);

	public TipoTarefa FindById(Long id);

	public TipoTarefa save(TipoTarefa tipoTarefa);

	public void deleteById(Long id);

}
