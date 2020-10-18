package com.digital.banco.nosso.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digital.banco.nosso.domain.exception.EnderecoNaoEncontradoException;
import com.digital.banco.nosso.domain.model.Endereco;
import com.digital.banco.nosso.domain.repository.EnderecoRepository;

@Service
public class CadastroEnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Transactional
	public Endereco salvar(Endereco endereco) {

		if(endereco.getCliente() != null) {
			endereco.getCliente().setEndereco(endereco);
		} 
		
		if(endereco != null) {
			enderecoRepository.save(endereco);
			enderecoRepository.flush();
		}

		return endereco;
	}

	public Endereco buscarOuFalhar(Long enderecoId) {

		return enderecoRepository.findById(enderecoId)
				.orElseThrow(() -> new EnderecoNaoEncontradoException(enderecoId));

	}

}
