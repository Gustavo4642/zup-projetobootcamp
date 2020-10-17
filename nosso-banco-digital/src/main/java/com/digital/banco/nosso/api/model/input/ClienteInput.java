package com.digital.banco.nosso.api.model.input;

import java.time.OffsetDateTime; 

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.br.CPF;

import com.digital.banco.nosso.core.validation.MaiorIdade;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClienteInput {

	@NotBlank
	private String nome;

	@NotBlank
	private String sobrenome;

	@NotBlank
	@Email(message = "E-mail do cliente está em formato inválido", 
		   regexp = "^[\\w\\-]+(\\.[\\w\\-]+)*@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}$")
	private String email;

	@NotNull
	@Past(message = "A data deve estar no passado!")
	@MaiorIdade
	private OffsetDateTime dataNascimento;

	@NotBlank
	@CPF
	private String cpf;

}
