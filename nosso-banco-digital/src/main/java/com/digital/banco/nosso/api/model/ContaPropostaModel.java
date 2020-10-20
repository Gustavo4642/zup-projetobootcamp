package com.digital.banco.nosso.api.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "contas")
@Setter
@Getter
public class ContaPropostaModel extends RepresentationModel<ContaPropostaModel>{

	private String codigo;
	private String agencia;
	private String numeroConta;
	private String codigoBanco;
	private BigDecimal saldo;
}