package br.com.claudemirojr.ticket.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.claudemirojr.ticket.model.entity.Cliente;
import br.com.claudemirojr.ticket.model.entity.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

	Page<Pessoa> findByIdGreaterThanEqual(Long id, Pageable pageable);

	Page<Pessoa> findByNomeIgnoreCaseContaining(String nome, Pageable pageable);

	Page<Pessoa> findByCliente(Cliente cliente, Pageable pageable);

	@Query(value = "SELECT COUNT(cs.sistema_id) AS QTD\n" 
					+ "FROM pessoa AS p\n"
					+ "		JOIN cliente AS c ON ( p.cliente_id = c.id )\n"
					+ "		JOIN cliente_sistema AS cs ON ( c.id = cs.cliente_id )\n" 
					+ "WHERE p.id = ?1 \n"
					+ "	   AND cs.sistema_id = ?2 ",
			nativeQuery = true)
	Long findByPessoaTemSistema(Long idPessoa, Long idSistema);

}
