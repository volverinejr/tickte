package br.com.claudemirojr.ticket.model.entity;

import java.io.Serializable;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pessoa_solicitacao", uniqueConstraints = @UniqueConstraint(columnNames = { "pessoa_id", "solicitacao_id" }))
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PessoaSolicitacao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	private Pessoa pessoa;

	@OneToOne(fetch = FetchType.LAZY)
	private Solicitacao solicitacao;

	@Column(name = "criado_em", updatable = false)
	private OffsetDateTime criadoEm;

	@PrePersist
	public void prePersist() {
		this.criadoEm = OffsetDateTime.now();
	}

}
