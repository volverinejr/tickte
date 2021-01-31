package br.com.claudemirojr.ticket.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "tarefa")
@Entity
public class Tarefa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	private SprintBacklog sprintBacklog;

	@OneToOne(fetch = FetchType.LAZY)
	private TipoTarefa tipoTarefa;

	@Column(length = 500, nullable = false)
	private String descricao;

	@Column(name = "prazo", nullable = false)
	private Date prazo;

	@OneToOne(fetch = FetchType.LAZY)
	private Pessoa pessoa;

	@Column(nullable = false)
	private Boolean finalizado;

	public void Atualizar(TipoTarefa tipoTarefa, String descricao, Date prazo, Pessoa pessoa) {
		this.tipoTarefa = tipoTarefa;
		this.descricao = descricao;
		this.prazo = prazo;
		this.pessoa = pessoa;
	}

}
