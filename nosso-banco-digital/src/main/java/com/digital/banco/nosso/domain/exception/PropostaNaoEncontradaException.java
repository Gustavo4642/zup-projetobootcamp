package com.digital.banco.nosso.domain.exception;

public class PropostaNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	public PropostaNaoEncontradaException(Long clienteId) {
		super(String.format("Proposta para o cliente de código: %d não encontrada" , clienteId));
	}
	
	public PropostaNaoEncontradaException(String cpfCliente) {
		super(String.format("Não foi encontrado uma proposta para o cliente com CPF: %s" , cpfCliente));
	}
}