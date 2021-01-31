package br.com.claudemirojr.ticket.model.entity.dto;

import java.io.Serializable;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import br.com.claudemirojr.ticket.model.entity.Pessoa;
import br.com.claudemirojr.ticket.model.entity.Time;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PessoaTimeDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@NotNull
	private Pessoa pessoa;

	@NotNull
	private Time time;

}
