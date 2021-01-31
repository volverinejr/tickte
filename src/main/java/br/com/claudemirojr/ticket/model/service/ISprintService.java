package br.com.claudemirojr.ticket.model.service;

import org.springframework.data.domain.Page;

import br.com.claudemirojr.ticket.model.ParamsRequestModel;
import br.com.claudemirojr.ticket.model.entity.Sprint;

public interface ISprintService {

	public Page<Sprint> findAll(ParamsRequestModel prm);

	public Page<Sprint> findByIdGreaterThanEqual(Long id, ParamsRequestModel prm);

	public Page<Sprint> findByNomeContaining(String nome, ParamsRequestModel prm);

	public Sprint FindById(Long id);

	public Sprint save(Sprint time);

	public void deleteById(Long id);

}
