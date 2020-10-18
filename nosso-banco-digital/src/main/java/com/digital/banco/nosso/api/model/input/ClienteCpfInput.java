package com.digital.banco.nosso.api.model.input;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.br.CPF;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClienteCpfInput {

	@NotBlank
	@CPF
	private String cpf;
}
