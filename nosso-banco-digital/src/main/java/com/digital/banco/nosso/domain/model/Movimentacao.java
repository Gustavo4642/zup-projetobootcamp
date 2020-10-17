package com.digital.banco.nosso.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class Movimentacao {

	private Long id;
	private BigDecimal valorTransferecia;
	private OffsetDateTime dataRealizacao;
	private String docIdentificadorOrigem;
	private String codigoBancoOrigem;
	private String agenciaBancoOrigem;
	private String contaBancoOrigem;
	private String idTransferenciaBancoOrigem;
	private Conta conta;
}
