package br.com.claudemirojr.ticket.model.service;

import java.util.List;

import br.com.claudemirojr.ticket.model.entity.Pessoa;
import br.com.claudemirojr.ticket.model.entity.PessoaTime;
import br.com.claudemirojr.ticket.model.entity.Time;

public interface IPessoaTimeService {

	public List<PessoaTime> findByPessoa(Pessoa pessoa);

	public PessoaTime FindById(Long id);

	public PessoaTime findByPessoaAndTime(Pessoa pessoa, Time time);

	public PessoaTime save(PessoaTime pessoaTime);

	public void deleteById(Long id);

}
