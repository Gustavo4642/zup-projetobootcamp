package com.digital.banco.nosso.domain.model;

public enum StatusCliente {

	ATIVO("Ativo"),
	INATIVO("Inativo"),
	ANALISE("Análise"),
	RECUSA("Cliente Recusou");
	
	private String descricao;
	
	StatusCliente(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}

}
