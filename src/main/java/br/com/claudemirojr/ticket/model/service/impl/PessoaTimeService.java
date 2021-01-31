package br.com.claudemirojr.ticket.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.claudemirojr.ticket.exception.NotFoundException;
import br.com.claudemirojr.ticket.model.entity.Pessoa;
import br.com.claudemirojr.ticket.model.entity.PessoaTime;
import br.com.claudemirojr.ticket.model.entity.Time;
import br.com.claudemirojr.ticket.model.repository.PessoaTimeRepository;
import br.com.claudemirojr.ticket.model.service.IPessoaTimeService;

@Service
public class PessoaTimeService implements IPessoaTimeService {

	@Autowired
	private PessoaTimeRepository _repository;

	@Override
	@Transactional(readOnly = true)
	public List<PessoaTime> findByPessoa(Pessoa pessoa) {
		return _repository.findByPessoa(pessoa);
	}

	@Override
	@Transactional(readOnly = true)
	public PessoaTime FindById(Long id) {
		return _repository.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format("PessoaTime não encontrado para id %d", id)));
	}

	@Override
	@Transactional(readOnly = true)
	public PessoaTime findByPessoaAndTime(Pessoa pessoa, Time time) {
		return _repository.findByPessoaAndTime(pessoa, time)
				.orElseThrow(() -> new NotFoundException(String.format("Conjunto Pessoa/Time não encontrado")));
	}

	@Override
	@Transactional(readOnly = false)
	public PessoaTime save(PessoaTime pessoaTime) {
		return _repository.save(pessoaTime);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteById(Long id) {
		_repository.deleteById(id);
	}

}
