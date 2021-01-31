package br.com.claudemirojr.ticket.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.claudemirojr.ticket.model.entity.Cliente;
import br.com.claudemirojr.ticket.model.entity.ClienteSistema;
import br.com.claudemirojr.ticket.model.entity.Sistema;

public interface ClienteSistemaRepository extends JpaRepository<ClienteSistema, Long> {

	List<ClienteSistema> findByCliente(Cliente cliente);

	Optional<ClienteSistema> findByClienteAndSistema(Cliente cliente, Sistema sistema);

}
