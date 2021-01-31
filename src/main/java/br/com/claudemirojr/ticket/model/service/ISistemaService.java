package br.com.claudemirojr.ticket.model.service;

import org.springframework.data.domain.Page;

import br.com.claudemirojr.ticket.model.ParamsRequestModel;
import br.com.claudemirojr.ticket.model.entity.Sistema;

public interface ISistemaService {

	public Page<Sistema> findAll(ParamsRequestModel prm);

	public Page<Sistema> findByIdGreaterThanEqual(Long id, ParamsRequestModel prm);

	public Page<Sistema> findByNomeContaining(String nome, ParamsRequestModel prm);

	public Page<Sistema> findByAtivo(Boolean ativo, ParamsRequestModel prm);

	public Sistema FindById(Long id);

	public Sistema save(Sistema sistema);

	public void deleteById(Long id);

}
