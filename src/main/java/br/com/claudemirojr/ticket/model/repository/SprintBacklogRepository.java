package br.com.claudemirojr.ticket.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.claudemirojr.ticket.model.entity.Sprint;
import br.com.claudemirojr.ticket.model.entity.SprintBacklog;

public interface SprintBacklogRepository extends JpaRepository<SprintBacklog, Long> {

	List<SprintBacklog> findBySprint(Sprint sprint);

	Optional<SprintBacklog> findByIdAndSprint(Long id, Sprint sprint);

}
