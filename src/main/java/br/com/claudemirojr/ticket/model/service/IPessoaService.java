package br.com.claudemirojr.ticket.model.service;

import org.springframework.data.domain.Page;

import br.com.claudemirojr.ticket.model.ParamsRequestModel;
import br.com.claudemirojr.ticket.model.entity.Cliente;
import br.com.claudemirojr.ticket.model.entity.Pessoa;

public interface IPessoaService {

	public Page<Pessoa> findAll(ParamsRequestModel prm);

	public Page<Pessoa> findByIdGreaterThanEqual(Long id, ParamsRequestModel prm);

	public Page<Pessoa> findByNomeContaining(String nome, ParamsRequestModel prm);

	public Page<Pessoa> findByCliente(Cliente cliente, ParamsRequestModel prm);

	public Pessoa FindById(Long id);

	public Pessoa save(Pessoa pessoa);

	public void deleteById(Long id);
	
	public Long findByPessoaTemSistema(Long idPessoa, Long idSistema);

}
