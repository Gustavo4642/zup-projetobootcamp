package com.digital.banco.nosso.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digital.banco.nosso.domain.exception.NegocioException;
import com.digital.banco.nosso.domain.model.Cliente;
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

		constroiMensagem.constroiMensagemClienteRecusado(cliente);
		
		cliente.recusar();
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
		
		constroiMensagem.constroiMensagemClienteAnalise(cliente);
		constroiMensagem.constroiMensagemAnaliseExterna(cliente, urlImagem);	

		cliente.analisar();
	}

	@Transactional
	public void ativarCliente(String cpfCliente) {
		Cliente cliente = buscarOuFalharCpf(cpfCliente);

		if (cliente.getStatus().equals(StatusCliente.ATIVO)) {
			throw new NegocioException(String.format("Cliente com CPF %s já está ATIVO", cliente.getCpf()));
		} else if (cliente.getStatus().equals(StatusCliente.INATIVO)) {
			throw new NegocioException(String.format("O Cliente deve ser enviado para análise.", cliente.getCpf()));
		} else if (cliente.getStatus().equals(StatusCliente.RECUSA)) {
			throw new NegocioException(String.format("O Cliente deve ser enviado para análise.", cliente.getCpf()));
		}

		cadastroCliente.verificaEnderecoExistente(cliente);
		cadastroFoto.verificaFotoExistente(cliente);
		
		constroiMensagem.constroiMensagemClienteAtivado(cliente);
		
		cliente.ativar();		
	}

	@Transactional
	public void inativarCliente(String cpfCliente) {
		Cliente cliente = buscarOuFalharCpf(cpfCliente);

		if (cliente.getStatus().equals(StatusCliente.INATIVO)) {
			throw new NegocioException(String.format("Cliente com CPF %s já está INATIVO", cliente.getCpf()));
		}

		cliente.inativar();
	}

	// validacao para pequisa
	public Cliente buscarOuFalharCpf(String cpfCliente) {

		Cliente clienteEncontrado = clienteRepository.procurePorCpfComEndereco(cpfCliente);

		if (clienteEncontrado == null) {
			throw new NegocioException(
					String.format("Cliente não foi encontrado. Verifique se o cadastro está completo."));
		}

		return clienteEncontrado;
	}

}
