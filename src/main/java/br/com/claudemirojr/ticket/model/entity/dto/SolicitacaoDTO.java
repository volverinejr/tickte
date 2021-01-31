package br.com.claudemirojr.ticket.model.entity.dto;

import java.io.Serializable;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import br.com.claudemirojr.ticket.model.entity.Pessoa;
import br.com.claudemirojr.ticket.model.entity.Sistema;
import br.com.claudemirojr.ticket.model.entity.enums.SolicitacaoStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SolicitacaoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@NotBlank
	@Size(min = 4, max = 2000)
	private String descricao;

	@NotNull
	private Pessoa pessoa;

	@NotNull
	private Sistema sistema;

	@Null
	private SolicitacaoStatus statusAtual;

	@Null
	private Integer prioridade;

}
