package com.digital.banco.nosso.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoClienteModel {
	
	private String nomeArquivo;
	private String descricao;
	private String contentType;
	private Long tamanho;
}
