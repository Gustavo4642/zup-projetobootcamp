package com.digital.banco.nosso.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.digital.banco.nosso.domain.model.StatusProposta;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "propostas")
@Setter
@Getter
public class PropostaContaModel extends RepresentationModel<PropostaContaModel>{

	private String codigo;
	private ClienteModel cliente;
	private StatusProposta statusProposta;
	private String motivo;
}