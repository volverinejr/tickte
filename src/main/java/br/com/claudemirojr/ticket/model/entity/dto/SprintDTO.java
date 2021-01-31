package br.com.claudemirojr.ticket.model.entity.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.claudemirojr.ticket.model.entity.Time;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SprintDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@NotBlank
	@Size(min = 4, max = 100)
	private String nome;

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataInicio;

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataFim;

	@NotNull
	private Time time;

	public String getDataInicioFormatada() {

		if (this.dataInicio != null) {
			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
			return formatador.format(this.dataInicio);
		}

		return null;
	}

	public String getDataFimFormatada() {

		if (this.dataFim != null) {
			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
			return formatador.format(this.dataFim);
		}

		return null;
	}

}
