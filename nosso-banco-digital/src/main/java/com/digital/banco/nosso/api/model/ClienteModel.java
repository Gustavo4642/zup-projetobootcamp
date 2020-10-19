package com.digital.banco.nosso.api.model;

import java.time.OffsetDateTime;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClienteModel {

	@ApiModelProperty(value = "Código do Cliente", example = "Gerado automáticamente")
	private String codigo;
	private String nome;
	private String sobrenome;
	private String email;
	private OffsetDateTime dataNascimento;
	private String cpf;
	private String statusCliente;
}