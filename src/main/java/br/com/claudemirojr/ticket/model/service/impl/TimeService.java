package br.com.claudemirojr.ticket.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.claudemirojr.ticket.exception.NotFoundException;
import br.com.claudemirojr.ticket.model.ParamsRequestModel;
import br.com.claudemirojr.ticket.model.entity.Time;
import br.com.claudemirojr.ticket.model.repository.TimeRepository;
import br.com.claudemirojr.ticket.model.service.ITimeService;

@Service
public class TimeService implements ITimeService {

	@Autowired
	private TimeRepository _repository;

	@Override
	@Transactional(readOnly = true)
	public Page<Time> findAll(ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		return _repository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Time> findByIdGreaterThanEqual(Long id, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		return _repository.findByIdGreaterThanEqual(id, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Time> findByNomeContaining(String nome, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		return _repository.findByNomeIgnoreCaseContaining(nome, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Time> findByAtivo(Boolean ativo, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		return _repository.findByAtivo(ativo, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Time FindById(Long id) {
		return _repository.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format("Time não encontrado para id %d", id)));
	}

	@Override
	@Transactional(readOnly = false)
	public Time save(Time time) {
		return _repository.save(time);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteById(Long id) {
		_repository.deleteById(id);
	}

}
