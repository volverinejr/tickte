package br.com.claudemirojr.ticket.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.claudemirojr.ticket.exception.NotFoundException;
import br.com.claudemirojr.ticket.model.entity.Cliente;
import br.com.claudemirojr.ticket.model.entity.ClienteSistema;
import br.com.claudemirojr.ticket.model.entity.Sistema;
import br.com.claudemirojr.ticket.model.repository.ClienteSistemaRepository;
import br.com.claudemirojr.ticket.model.service.IClienteSistemaService;

@Service
public class ClienteSistemaService implements IClienteSistemaService {

	@Autowired
	private ClienteSistemaRepository _repository;

	@Override
	@Transactional(readOnly = true)
	public List<ClienteSistema> findByCliente(Cliente cliente) {
		return _repository.findByCliente(cliente);
	}

	@Override
	@Transactional(readOnly = true)
	public ClienteSistema FindById(Long id) {
		return _repository.findById(id).orElseThrow(
				() -> new NotFoundException(String.format("ClienteSistema não encontrado para id %d", id)));
	}

	@Override
	@Transactional(readOnly = true)
	public ClienteSistema findByClienteAndSistema(Cliente cliente, Sistema sistema) {
		return _repository.findByClienteAndSistema(cliente, sistema)
				.orElseThrow(() -> new NotFoundException(String.format("Conjunto Cliente/Sistema não encontrado")));
	}

	@Override
	@Transactional(readOnly = false)
	public ClienteSistema save(ClienteSistema clienteSistema) {
		return _repository.save(clienteSistema);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteById(Long id) {
		_repository.deleteById(id);
	}

}
