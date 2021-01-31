package br.com.claudemirojr.ticket.model.entity.dto;

import java.io.Serializable;
import java.time.OffsetDateTime;

import br.com.claudemirojr.ticket.model.entity.enums.SolicitacaoStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SolicitacaoMovimentacaoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private SolicitacaoStatus status;

	private OffsetDateTime criadoEm;

}
