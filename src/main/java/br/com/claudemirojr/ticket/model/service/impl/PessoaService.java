package br.com.claudemirojr.ticket.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.claudemirojr.ticket.exception.NotFoundException;
import br.com.claudemirojr.ticket.model.ParamsRequestModel;
import br.com.claudemirojr.ticket.model.entity.Cliente;
import br.com.claudemirojr.ticket.model.entity.Pessoa;
import br.com.claudemirojr.ticket.model.repository.PessoaRepository;
import br.com.claudemirojr.ticket.model.service.IPessoaService;

@Service
public class PessoaService implements IPessoaService {

	@Autowired
	private PessoaRepository _repository;

	@Override
	@Transactional(readOnly = true)
	public Page<Pessoa> findAll(ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		return _repository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Pessoa> findByIdGreaterThanEqual(Long id, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		return _repository.findByIdGreaterThanEqual(id, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Pessoa> findByNomeContaining(String nome, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		return _repository.findByNomeIgnoreCaseContaining(nome, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Pessoa> findByCliente(Cliente cliente, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		return _repository.findByCliente(cliente, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Pessoa FindById(Long id) {
		return _repository.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format("Pessoa não encontrada para id %d", id)));
	}

	@Override
	@Transactional(readOnly = false)
	public Pessoa save(Pessoa pessoa) {
		return _repository.save(pessoa);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteById(Long id) {
		_repository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Long findByPessoaTemSistema(Long idPessoa, Long idSistema) {
		return _repository.findByPessoaTemSistema(idPessoa, idSistema);
	}

}
