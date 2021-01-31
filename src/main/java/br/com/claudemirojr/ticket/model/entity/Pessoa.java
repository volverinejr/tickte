package br.com.claudemirojr.ticket.model.entity;

import java.io.Serializable;

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
@Table(name = "pessoa")
@Entity
public class Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 100, nullable = false)
	private String nome;

	@Column(nullable = false)
	private Boolean ativo;

	@OneToOne(fetch = FetchType.LAZY)
	private Cliente cliente;

/*	@OneToMany
	@Getter(onMethod = @__({ @JsonIgnore }))
	private List<Solicitacao> Solicitacoes = new ArrayList<Solicitacao>();

	
	 * @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	 * 
	 * @JoinTable( name = "pessoas_times", joinColumns = {@JoinColumn(name =
	 * "pessoa_id", referencedColumnName = "id", nullable = false, updatable =
	 * false)}, inverseJoinColumns = {@JoinColumn(name = "time_id",
	 * referencedColumnName = "id", nullable = false, updatable = false)} )
	 * 
	 * @Getter(onMethod = @__({@JsonIgnore})) private List<Time> times = new
	 * ArrayList<Time>();
	 * 
	 * 
	 * 
	 * public void addTime(Time time) {
	 * 
	 * if (!this.times.contains(time)) { this.times.add(time); } }
	 * 
	 * public void removeTime(Time time) {
	 * 
	 * if (this.times.contains(time)) { this.times.remove(time); } }
	 */

	public void Atualizar(String nome, Boolean ativo, Cliente cliente) {
		this.nome = nome;
		this.ativo = ativo;
		this.cliente = cliente;
	}

}
