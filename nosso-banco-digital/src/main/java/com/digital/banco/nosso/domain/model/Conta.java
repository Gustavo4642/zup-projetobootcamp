package com.digital.banco.nosso.domain.model;

import java.math.BigDecimal;

public class Conta {

	private Long id;
	private String agencia;
	private String numeroConta;
	private String codigoBanco;
	private BigDecimal saldo;
	private Proposta proposta;
	private Movimentacao movimentacao;
}
