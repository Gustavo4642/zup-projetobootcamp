package com.digital.banco.nosso.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.digital.banco.nosso.domain.model.StatusProposta;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "propostas")
@Setter
@Getter
public class PropostaModel extends RepresentationModel<PropostaModel>{

	private String codigo;
	private ClienteModel cliente;
	private ContaPropostaModel conta;
	private StatusProposta statusProposta;
	private String motivo;
}