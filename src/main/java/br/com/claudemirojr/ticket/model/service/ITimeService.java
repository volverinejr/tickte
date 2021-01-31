package br.com.claudemirojr.ticket.model.service;

import org.springframework.data.domain.Page;

import br.com.claudemirojr.ticket.model.ParamsRequestModel;
import br.com.claudemirojr.ticket.model.entity.Time;

public interface ITimeService {

	public Page<Time> findAll(ParamsRequestModel prm);

	public Page<Time> findByIdGreaterThanEqual(Long id, ParamsRequestModel prm);

	public Page<Time> findByNomeContaining(String nome, ParamsRequestModel prm);

	public Page<Time> findByAtivo(Boolean ativo, ParamsRequestModel prm);

	public Time FindById(Long id);

	public Time save(Time time);

	public void deleteById(Long id);

}
