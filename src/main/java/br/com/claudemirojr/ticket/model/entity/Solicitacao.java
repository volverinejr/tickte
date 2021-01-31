package br.com.claudemirojr.ticket.model.entity;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.claudemirojr.ticket.model.entity.enums.SolicitacaoStatus;
import lombok.Data;

@Data
@Table(name = "solicitacao")
@Entity
public class Solicitacao implements Serializable {

	private static final long serialVersionUID = 1L;

	public Solicitacao() {
		super();
	}

	public Solicitacao(String descricao, Pessoa pessoa, Sistema sistema) {
		this.descricao = descricao;
		this.pessoa = pessoa;
		this.sistema = sistema;
		this.statusAtual = SolicitacaoStatus.CADASTRADA;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 500, nullable = false)
	private String descricao;

	@OneToOne(fetch = FetchType.LAZY)
	private Pessoa pessoa;

	@OneToOne(fetch = FetchType.LAZY)
	private Sistema sistema;

	@Column(name = "prazo_expectativa", nullable = true)
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date prazoExpectativa;

	@Column(name = "analise_risco", nullable = true)
	private String analiseRisco;

	@Column(nullable = true)
	private Integer prioridade;

	@Column(name = "status_atual", length = 30, nullable = true)
	@Enumerated(EnumType.STRING)
	private SolicitacaoStatus statusAtual;

	@Column(name = "criado_em", updatable = false)
	private OffsetDateTime criadoEm;

	@PrePersist
	public void prePersist() {
		this.criadoEm = OffsetDateTime.now();
	}

	/*
	 * @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	 * 
	 * @JoinTable( name = "solicitacaos_times", joinColumns = {@JoinColumn(name =
	 * "solicitacao_id", referencedColumnName = "id", nullable = false, updatable =
	 * false)}, inverseJoinColumns = {@JoinColumn(name = "time_id",
	 * referencedColumnName = "id", nullable = false, updatable = false)} ) private
	 * List<Time> times = new ArrayList<Time>();
	 * 
	 * 
	 * public void addTime(Time time) {
	 * 
	 * if (!this.times.contains(time)) { this.times.add(time); } }
	 * 
	 * 
	 */

	public void Atualizar(String descricao, Sistema sistema) {
		this.descricao = descricao;
		this.sistema = sistema;
	}

	public void Analise(Date prazoExpectativa, String analiseRisco, Integer prioridade) {
		this.prazoExpectativa = prazoExpectativa;
		this.analiseRisco = analiseRisco;
		this.prioridade = prioridade;
		this.statusAtual = SolicitacaoStatus.ANALISADA;
	}

	public void AtualizarUltimaMovimentacao(SolicitacaoStatus statusAtual) {
		this.statusAtual = statusAtual;
	}

}
