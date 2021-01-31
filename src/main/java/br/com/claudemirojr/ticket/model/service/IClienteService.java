package br.com.claudemirojr.ticket.model.service;

import org.springframework.data.domain.Page;

import br.com.claudemirojr.ticket.model.ParamsRequestModel;
import br.com.claudemirojr.ticket.model.entity.Cliente;

public interface IClienteService {

	public Page<Cliente> findAll(ParamsRequestModel prm);

	public Page<Cliente> findByIdGreaterThanEqual(Long id, ParamsRequestModel prm);

	public Page<Cliente> findByNomeContaining(String nome, ParamsRequestModel prm);

	public Page<Cliente> findByAtivo(Boolean ativo, ParamsRequestModel prm);

	public Cliente FindById(Long id);

	public Cliente save(Cliente cliente);

	public void deleteById(Long id);

}
