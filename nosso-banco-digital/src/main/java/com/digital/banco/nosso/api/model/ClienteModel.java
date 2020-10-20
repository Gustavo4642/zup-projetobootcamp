package com.digital.banco.nosso.api.model;

import java.time.OffsetDateTime;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "clientes")
@Setter
@Getter
public class ClienteModel extends RepresentationModel<ClienteModel>{

	@ApiModelProperty(value = "Código do Cliente", example = "Gerado automáticamente")
	private String codigo;
	private String nome;
	private String sobrenome;
	private String email;
	private OffsetDateTime dataNascimento;
	private String cpf;
	private String statusCliente;
}