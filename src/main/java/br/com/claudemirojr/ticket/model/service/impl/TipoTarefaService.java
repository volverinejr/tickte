package br.com.claudemirojr.ticket.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.claudemirojr.ticket.exception.NotFoundException;
import br.com.claudemirojr.ticket.model.ParamsRequestModel;
import br.com.claudemirojr.ticket.model.entity.TipoTarefa;
import br.com.claudemirojr.ticket.model.repository.TipoTarefaRepository;
import br.com.claudemirojr.ticket.model.service.ITipoTarefaService;

@Service
public class TipoTarefaService implements ITipoTarefaService {

	@Autowired
	private TipoTarefaRepository _repository;

	@Override
	@Transactional(readOnly = true)
	public Page<TipoTarefa> findAll(ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		return _repository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<TipoTarefa> findByIdGreaterThanEqual(Long id, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		return _repository.findByIdGreaterThanEqual(id, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<TipoTarefa> findByNomeContaining(String nome, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		return _repository.findByNomeIgnoreCaseContaining(nome, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<TipoTarefa> findByAtivo(Boolean ativo, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		return _repository.findByAtivo(ativo, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public TipoTarefa FindById(Long id) {
		return _repository.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format("TipoTarefa não encontrado para id %d", id)));
	}

	@Override
	@Transactional(readOnly = false)
	public TipoTarefa save(TipoTarefa tipoTarefa) {
		return _repository.save(tipoTarefa);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteById(Long id) {
		_repository.deleteById(id);
	}

}
