package br.com.claudemirojr.ticket.model.entity.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import br.com.claudemirojr.ticket.model.entity.Pessoa;
import br.com.claudemirojr.ticket.model.entity.Sistema;
import br.com.claudemirojr.ticket.model.entity.enums.SolicitacaoStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SolicitacaoAnaliseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String descricao;

	private Pessoa pessoa;

	private Sistema sistema;

	private SolicitacaoStatus statusAtual;

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date prazoExpectativa;

	@NotNull
	@Size(min = 4, max = 500)
	private String analiseRisco;

	@NotNull
	@Range(min = 1, max = 10)
	private Integer prioridade;

	public String getPrazoExpectativaFormatada() {
		
		if ( this.prazoExpectativa != null ) {
			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
			return formatador.format(this.prazoExpectativa);
		}
		
		return null;
	}
}
