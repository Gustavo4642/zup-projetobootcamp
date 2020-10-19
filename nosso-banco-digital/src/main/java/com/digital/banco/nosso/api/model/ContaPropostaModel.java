package com.digital.banco.nosso.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ContaPropostaModel {

	private String codigo;
	private String agencia;
	private String numeroConta;
	private String codigoBanco;
	private BigDecimal saldo;
}