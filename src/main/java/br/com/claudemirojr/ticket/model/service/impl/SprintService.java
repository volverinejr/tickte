package br.com.claudemirojr.ticket.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.claudemirojr.ticket.exception.NotFoundException;
import br.com.claudemirojr.ticket.model.ParamsRequestModel;
import br.com.claudemirojr.ticket.model.entity.Sprint;
import br.com.claudemirojr.ticket.model.repository.SprintRepository;
import br.com.claudemirojr.ticket.model.service.ISprintService;

@Service
public class SprintService implements ISprintService {

	@Autowired
	private SprintRepository _repository;

	@Override
	@Transactional(readOnly = true)
	public Page<Sprint> findAll(ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		return _repository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Sprint> findByNomeContaining(String nome, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		return _repository.findByNomeIgnoreCaseContaining(nome, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Sprint FindById(Long id) {
		return _repository.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format("Sprint não encontrado para id %d", id)));
	}

	@Override
	@Transactional(readOnly = false)
	public Sprint save(Sprint sprint) {
		return _repository.save(sprint);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteById(Long id) {
		_repository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Sprint> findByIdGreaterThanEqual(Long id, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		return _repository.findByIdGreaterThanEqual(id, pageable);
	}

}
