package com.digital.banco.nosso.domain.exception;

public class FotoClienteNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public FotoClienteNaoEncontradaException(String codigoCliente) {
		super(String.format("Não existe um cadastro de foto do cliente com código %ss.", codigoCliente));
	}
}
