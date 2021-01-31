package br.com.claudemirojr.ticket.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.claudemirojr.ticket.model.entity.Pessoa;
import br.com.claudemirojr.ticket.model.entity.PessoaTime;
import br.com.claudemirojr.ticket.model.entity.Time;

public interface PessoaTimeRepository extends JpaRepository<PessoaTime, Long> {

	List<PessoaTime> findByPessoa(Pessoa Pessoa);

	Optional<PessoaTime> findByPessoaAndTime(Pessoa Pessoa, Time time);

}
