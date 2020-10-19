package com.digital.banco.nosso.api.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnderecoInput {

	@ApiModelProperty(example = "Formato do CEP '99999-999", required = true)
	@NotBlank
	@Pattern(regexp = "([0-9]{5}[-]?[0-9]{3})", message = "Formato de CEP inválido")
	private String cep;

	@ApiModelProperty(required = true)
	@NotBlank
	private String rua;

	@ApiModelProperty(required = true)
	@NotBlank
	private String numero;

	@ApiModelProperty(required = true)
	@NotBlank
	private String bairro;

	@ApiModelProperty(required = true)
	@NotBlank
	private String complemento;

	@ApiModelProperty(required = true)
	@NotBlank
	private String cidade;

	@ApiModelProperty(required = true)
	@NotBlank
	private String estado;

	@ApiModelProperty(required = true)
	@NotBlank
	private String cpf;

}
