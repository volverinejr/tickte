package br.com.claudemirojr.ticket.model.entity.dto;

import java.io.Serializable;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import br.com.claudemirojr.ticket.model.entity.Cliente;
import br.com.claudemirojr.ticket.model.entity.Sistema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClienteSistemaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@NotNull
	private Cliente cliente;

	@NotNull
	private Sistema sistema;

}
