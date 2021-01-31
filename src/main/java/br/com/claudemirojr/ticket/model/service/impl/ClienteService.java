package br.com.claudemirojr.ticket.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.claudemirojr.ticket.exception.NotFoundException;
import br.com.claudemirojr.ticket.model.ParamsRequestModel;
import br.com.claudemirojr.ticket.model.entity.Cliente;
import br.com.claudemirojr.ticket.model.repository.ClienteRepository;
import br.com.claudemirojr.ticket.model.service.IClienteService;

@Service
public class ClienteService implements IClienteService {

	@Autowired
	private ClienteRepository _repository;

	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findAll(ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		return _repository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findByNomeContaining(String nome, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		return _repository.findByNomeIgnoreCaseContaining(nome, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findByIdGreaterThanEqual(Long id, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		return _repository.findByIdGreaterThanEqual(id, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findByAtivo(Boolean ativo, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		return _repository.findByAtivo(ativo, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente FindById(Long id) {
		return _repository.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format("Cliente não encontrado para id %d", id)));
	}

	@Override
	@Transactional(readOnly = false)
	public Cliente save(Cliente cliente) {
		return _repository.save(cliente);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteById(Long id) {
		_repository.deleteById(id);
	}

}
