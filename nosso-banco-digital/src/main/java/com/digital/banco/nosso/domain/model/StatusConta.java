package com.digital.banco.nosso.domain.model;

public enum StatusConta {

	ABERTA("Aberta"),
	CANECELADA("Cancelada");
	
	private String descricao;
	
	StatusConta(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}

}
