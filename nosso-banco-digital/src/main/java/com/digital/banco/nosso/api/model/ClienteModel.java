package com.digital.banco.nosso.api.model;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClienteModel {

	private String codigo;
	private String nome;
	private String sobrenome;
	private String email;
	private OffsetDateTime dataNascimento;
	private String cpf;
	private String statusCliente;
}