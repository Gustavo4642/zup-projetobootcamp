package com.digital.banco.nosso.domain.exception;

public class ContaNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	public ContaNaoEncontradaException(Long contaId) {
		super(String.format("Conta para o cliente de código: %d não encontrada" , contaId));
	}
	
	public ContaNaoEncontradaException(String codigoConta) {
		super(String.format("Conta para o cliente de código: %d não encontrada" , codigoConta));
	}
	 
	
}