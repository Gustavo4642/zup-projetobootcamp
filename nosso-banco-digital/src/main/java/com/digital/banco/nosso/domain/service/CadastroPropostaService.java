package com.digital.banco.nosso.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.digital.banco.nosso.domain.exception.EntidadeEmUsoException;
import com.digital.banco.nosso.domain.exception.PropostaNaoEncontradaException;
import com.digital.banco.nosso.domain.model.Cliente;
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
	public void excluir(Long propostaId) {
		try {
			propostaRepository.deleteById(propostaId);
			propostaRepository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new PropostaNaoEncontradaException(propostaId);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Proposta %d está em uso.", propostaId));
		}
	}
	
	private Proposta montaProposta(Cliente cliente) {

		Proposta proposta = new Proposta();

		proposta.setCliente(cliente);
		proposta.setStatusProposta(StatusProposta.AGUARDANDO);
		proposta.setMovito("Proposta enviada para análise.");

		return proposta;
	}

	public Proposta buscarOuFalharCpfCliente(String cpfCliente) {
		return propostaRepository.findByCpfCliente(cpfCliente)
				.orElseThrow(() -> new PropostaNaoEncontradaException(cpfCliente));
	}
	
	public Proposta buscarPropostaParaExclusao(String cpfCliente) {
		return propostaRepository.findByCpfExclusao(cpfCliente);
	}
}
