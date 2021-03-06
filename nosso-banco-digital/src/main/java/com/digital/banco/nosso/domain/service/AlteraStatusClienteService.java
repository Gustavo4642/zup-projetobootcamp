package com.digital.banco.nosso.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digital.banco.nosso.domain.exception.NegocioException;
import com.digital.banco.nosso.domain.model.Cliente;
import com.digital.banco.nosso.domain.model.Conta;
import com.digital.banco.nosso.domain.model.StatusCliente;
import com.digital.banco.nosso.domain.repository.ClienteRepository;
import com.digital.banco.nosso.domain.service.mensagemEmail.ConstroiMensagemEmail;

@Service
public class AlteraStatusClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CadastroClienteService cadastroCliente;

	@Autowired
	private CadastroFotoClienteService cadastroFoto;
	
	@Autowired
	private ConstroiMensagemEmail constroiMensagem;

	@Transactional
	public void recusadoPeloCliente(String cpfCliente) {

		Cliente cliente = clienteRepository.procurePorCpf(cpfCliente);

		if (cliente.getStatus().equals(StatusCliente.ATIVO)) {
			throw new NegocioException(String.format("Cliente com CPF %s já está ATIVO", cliente.getCpf()));
		}

		cliente.recusar();
		clienteRepository.flush();
		
		constroiMensagem.constroiMensagemClienteRecusado(cliente);
		
	}

	@Transactional
	public void aguardandoAnaliseCliente(Cliente cliente, String urlImagem) {

		if (cliente.getStatus().equals(StatusCliente.ANALISE)) {
			throw new NegocioException(String.format("Cliente com CPF %s já está em ANÁLISE", cliente.getCpf()));
		} else if (cliente.getStatus().equals(StatusCliente.ATIVO)) {
			throw new NegocioException(String.format("Cliente com CPF %s já está ATIVO", cliente.getCpf()));
		}
		
		cadastroCliente.verificaEnderecoExistente(cliente);
		cadastroFoto.verificaFotoExistente(cliente);
		
		cliente.analisar();
		clienteRepository.flush();
		
		constroiMensagem.constroiMensagemClienteAnalise(cliente);
		constroiMensagem.constroiMensagemAnaliseExterna(cliente, urlImagem);	
	}

	@Transactional
	public Cliente ativarCliente(Cliente cliente, Conta conta) {
		

		if (cliente.getStatus().equals(StatusCliente.ATIVO)) {
			throw new NegocioException(String.format("Cliente com CPF %s já está ATIVO", cliente.getCpf()));
		} else if (cliente.getStatus().equals(StatusCliente.INATIVO)) {
			throw new NegocioException(String.format("O Cliente deve ser enviado para análise.", cliente.getCpf()));
		} else if (cliente.getStatus().equals(StatusCliente.RECUSA)) {
			throw new NegocioException(String.format("O Cliente deve ser enviado para análise.", cliente.getCpf()));
		}
		
		cliente.ativar();
		clienteRepository.flush();
		
		constroiMensagem.constroiMensagemClienteAtivado(cliente, conta);
		
		return cliente;
	}

	@Transactional
	public void inativarCliente(String cpfCliente) {
		Cliente cliente = cadastroCliente.buscarOuFalharCpf(cpfCliente);

		if (cliente.getStatus().equals(StatusCliente.INATIVO)) {
			throw new NegocioException(String.format("Cliente com CPF %s já está INATIVO", cliente.getCpf()));
		}

		cliente.inativar();
	}

}
