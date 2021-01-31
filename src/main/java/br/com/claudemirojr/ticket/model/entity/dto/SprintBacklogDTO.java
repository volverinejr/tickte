package br.com.claudemirojr.ticket.model.entity.dto;

import java.io.Serializable;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import br.com.claudemirojr.ticket.model.entity.Backlog;
import br.com.claudemirojr.ticket.model.entity.Sprint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SprintBacklogDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@NotNull
	private Sprint sprint;

	@NotNull
	private Backlog solicitacaoBacklog;

	private Boolean finalizado;

}
