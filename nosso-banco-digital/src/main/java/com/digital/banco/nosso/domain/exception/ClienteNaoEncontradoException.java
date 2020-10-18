package com.digital.banco.nosso.domain.exception;

public class ClienteNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	public ClienteNaoEncontradoException(String codigoCliente) {
		super(String.format("Não existe um cadastro de cliente com CPF: %s" , codigoCliente));
	}
}