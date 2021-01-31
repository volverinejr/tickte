package br.com.claudemirojr.ticket.model.entity;

import java.io.Serializable;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "backlog")
@Entity
public class Backlog implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	private Solicitacao solicitacao;

	@Column(length = 500, nullable = false)
	private String descricao;

	@Column(name = "criado_em", updatable = false)
	private OffsetDateTime criadoEm;

	@PrePersist
	public void prePersist() {
		this.criadoEm = OffsetDateTime.now();
	}

	public void Atualizar(String descricao) {
		this.descricao = descricao;
	}

}
