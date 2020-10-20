package com.digital.banco.nosso.api.model;

import java.time.OffsetDateTime;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClienteExibicaoModel extends RepresentationModel<ClienteExibicaoModel>{

	@ApiModelProperty(value = "Código do Cliente", example = "Gerado automáticamente")
	private String codigo;
	private String nome;
	private String sobrenome;
	private String email;
	private OffsetDateTime dataNascimento;
	private String cpf;
	private EnderecoModel endereco;
	private String status;
	private String urlImagem;
}