package com.digital.banco.nosso.api.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PropostaModel {

	private String codigo;
	private ClienteModel clienteModel;
	private boolean aceite;
	private String motivo;
}