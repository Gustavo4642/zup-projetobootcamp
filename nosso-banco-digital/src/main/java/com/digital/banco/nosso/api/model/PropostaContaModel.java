package com.digital.banco.nosso.api.model;

import com.digital.banco.nosso.domain.model.StatusProposta;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PropostaContaModel {

	private String codigo;
	private ClienteModel cliente;
	private StatusProposta statusProposta;
	private String motivo;
}