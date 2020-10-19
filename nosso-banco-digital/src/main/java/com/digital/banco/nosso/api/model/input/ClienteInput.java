package com.digital.banco.nosso.api.model.input;

import java.time.OffsetDateTime; 

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.br.CPF;

import com.digital.banco.nosso.core.validation.MaiorIdade;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClienteInput {

	@ApiModelProperty(example = "João", required = true)
	@NotBlank
	private String nome;

	@ApiModelProperty(example = "Da Silva", required = true)
	@NotBlank
	private String sobrenome;

	@ApiModelProperty(example = "Somente formato de e-mail", required = true)
	@NotBlank
	@Email(message = "E-mail do cliente está em formato inválido", 
		   regexp = "^[\\w\\-]+(\\.[\\w\\-]+)*@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}$")
	private String email;

	@ApiModelProperty(example = "Cliente deve ter mais de 18 anos", required = true)
	@NotNull
	@Past(message = "A data deve estar no passado!")
	@MaiorIdade
	private OffsetDateTime dataNascimento;
	
	@ApiModelProperty(example = "Informe o CPF no formato '999.999.999-99'", required = true)
	@NotBlank
	@CPF
	private String cpf;

}
