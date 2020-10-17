package com.digital.banco.nosso.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digital.banco.nosso.domain.exception.EnderecoNaoEncontradoException;
import com.digital.banco.nosso.domain.model.Cliente;
import com.digital.banco.nosso.domain.model.Endereco;
import com.digital.banco.nosso.domain.repository.ClienteRepository;
import com.digital.banco.nosso.domain.repository.EnderecoRepository;

@Service
public class CadastroEnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private CadastroClienteService cadastroCliente;
	
	@Transactional
	public Endereco salvar(Endereco endereco) {

		Cliente cliente = cadastroCliente.buscarOuFalharCpf(endereco.getCliente().getCpf());
	
		if(endereco != null) {
			enderecoRepository.save(endereco);
			enderecoRepository.flush();
		}
		
		if(cliente != null) {
			cliente.setEndereco(endereco);
			clienteRepository.save(cliente);
			clienteRepository.flush();
		}
		
		return endereco;
	}

	public Endereco buscarOuFalhar(Long enderecoId) {

		return enderecoRepository.findById(enderecoId)
				.orElseThrow(() -> new EnderecoNaoEncontradoException(enderecoId));

	}

}
