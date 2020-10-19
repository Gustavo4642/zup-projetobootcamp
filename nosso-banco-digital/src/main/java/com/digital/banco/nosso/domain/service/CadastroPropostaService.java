package com.digital.banco.nosso.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.digital.banco.nosso.domain.exception.PropostaNaoEncontradaException;
import com.digital.banco.nosso.domain.model.Cliente;
import com.digital.banco.nosso.domain.model.Conta;
import com.digital.banco.nosso.domain.model.Proposta;
import com.digital.banco.nosso.domain.model.StatusProposta;
import com.digital.banco.nosso.domain.repository.PropostaRepository;

@Service
public class CadastroPropostaService {

	@Autowired
	private PropostaRepository propostaRepository;

	@Transactional
	public Proposta salvar(Cliente cliente) {

		Proposta proposta = new Proposta();
		proposta = montaProposta(cliente);
		return propostaRepository.save(proposta);		
	}
	
	@Transactional
	public void alteraStatus(Proposta proposta) {
		try {
			proposta.setStatusProposta(StatusProposta.REJEITADA);
			proposta.setMotivo("Proposta rejeitada");

		} catch (EmptyResultDataAccessException e) {
			throw new PropostaNaoEncontradaException(proposta.getCodigo());
		} 
	}
	
	private Proposta montaProposta(Cliente cliente) {

		Proposta proposta = new Proposta();

		proposta.setCliente(cliente);
		proposta.setStatusProposta(StatusProposta.AGUARDANDO);
		proposta.setMotivo("Proposta enviada para análise.");

		return proposta;
	}

	public void atualizaProposta(Proposta proposta, Conta conta) {

		proposta.setStatusProposta(StatusProposta.ACEITA);
		proposta.setMotivo(
				"Conta número '" 
				+ conta.getNumeroConta() 
				+"', para o banco '"
				+ conta.getCodigoBanco()
				+"', agencia '" 
				+ conta.getAgencia()
				+"', criada com sucesso!");
		proposta.setConta(conta);
		propostaRepository.save(proposta);
		
	}
	
	public Proposta buscarOuFalharCpfCliente(String cpfCliente) {
		return propostaRepository.findByCpfCliente(cpfCliente)
				.orElseThrow(() -> new PropostaNaoEncontradaException(cpfCliente));
	}
	
	public Proposta buscarPropostaNaoOptional(String cpfCliente) {
		return propostaRepository.findByCpfNaoOptional(cpfCliente);
	}
	
	public Proposta buscarPropostaPorCodigo(String codigoProposta) {
		return propostaRepository.buscarPropostaPorCodigo(codigoProposta);
	}
}
