package br.com.claudemirojr.ticket.model.entity.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CodigoEntidadeDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	private Long id;

}
