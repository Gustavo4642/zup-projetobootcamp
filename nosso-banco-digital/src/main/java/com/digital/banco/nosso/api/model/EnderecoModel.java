package com.digital.banco.nosso.api.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnderecoModel {

	private String cep;
	private String rua;
	private String numero;
	private String bairro;
	private String complemento;
	private String cidade;
	private String estado;
}