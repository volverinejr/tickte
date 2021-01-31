package br.com.claudemirojr.ticket.model.service;

import java.util.List;

import br.com.claudemirojr.ticket.model.entity.Cliente;
import br.com.claudemirojr.ticket.model.entity.ClienteSistema;
import br.com.claudemirojr.ticket.model.entity.Sistema;

public interface IClienteSistemaService {

	public List<ClienteSistema> findByCliente(Cliente cliente);

	public ClienteSistema FindById(Long id);

	public ClienteSistema findByClienteAndSistema(Cliente cliente, Sistema sistema);

	public ClienteSistema save(ClienteSistema clienteSistema);
	
	public void deleteById(Long id);

}
