package com.digital.banco.nosso.domain.model;

public enum StatusProposta {

	ACEITA("Aceita"),
	AGUARDANDO("Aguardando"),
	REJEITADA("Rejeitada");
	
	private String descricao;
	
	StatusProposta(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}

}
