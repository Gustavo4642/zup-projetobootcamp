package com.digital.banco.nosso.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PropostaInput {

	@Valid
	@NotBlank
	private ClienteCpfInput cliente;

	@NotNull
	private boolean aceite;
	
	@NotBlank
	private String motivo;

}
