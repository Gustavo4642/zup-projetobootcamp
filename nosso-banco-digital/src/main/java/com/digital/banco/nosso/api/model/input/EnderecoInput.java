package com.digital.banco.nosso.api.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnderecoInput {

	@NotBlank
	@Pattern(regexp = "([0-9]{5}[-]?[0-9]{3})", message = "Formato de CEP inválido")
	private String cep;

	@NotBlank
	private String rua;

	@NotBlank
	private String numero;

	@NotBlank
	private String bairro;

	@NotBlank
	private String complemento;

	@NotBlank
	private String cidade;

	@NotBlank
	private String estado;
	
	@NotBlank
	private String cpf;

}
