package com.digital.banco.nosso.domain.service;

import java.math.BigDecimal;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digital.banco.nosso.domain.exception.ContaNaoEncontradaException;
import com.digital.banco.nosso.domain.exception.PropostaNaoEncontradaException;
import com.digital.banco.nosso.domain.model.Cliente;
import com.digital.banco.nosso.domain.model.Conta;
import com.digital.banco.nosso.domain.model.Proposta;
import com.digital.banco.nosso.domain.repository.ContaRepository;

@Service
public class CadastroContaService {

	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private CadastroPropostaService cadastroProposta;

	@Transactional
	public Conta salvar(Conta conta) {
		return contaRepository.save(conta);		
		
	}
	
	@Transactional
	public Conta criaConta(Cliente cliente) {
		
		// TODO está fazendo um join em conta, tentar resolver com NO_PROXY e PersistentAttributeInterceptable
		Proposta proposta = cadastroProposta.buscarPropostaNaoOptional(cliente.getCpf());

		if(proposta == null) {
			throw new PropostaNaoEncontradaException(cliente.getCpf());
		}
		
		Conta conta = criar(proposta);
		contaRepository.save(conta);
		contaRepository.flush();
		
		cadastroProposta.atualizaProposta(proposta, conta);
		
		return conta;
	}
	
	public Conta buscaContaProposta(String codigoConta) {
		Conta contaEncontrada = contaRepository.procureContaProposta(codigoConta);

		if (contaEncontrada != null) {
			throw new ContaNaoEncontradaException(codigoConta);
		}
		
		return contaEncontrada;
	}
	
	private Conta criar(Proposta proposta) {
		
		Conta conta = new Conta();
		
		conta.setAgencia(criaAgencia());
		conta.setNumeroConta(criaNumeroConta());
		conta.setCodigoBanco("001");
		conta.setProposta(proposta);
		conta.setSaldo(BigDecimal.ZERO);
		
		return conta;
	}
	
	private String criaAgencia() {
		Random numeroAleatorio = new Random(); 
		int agenciaAleatoria;
		int digitoAleatorio;
		
		agenciaAleatoria = numeroAleatorio.nextInt(999);
		digitoAleatorio = numeroAleatorio.nextInt(10);
		
		StringBuilder agenciaGerada = new StringBuilder();
		agenciaGerada.append(agenciaAleatoria);
		agenciaGerada.append("-");
		agenciaGerada.append(digitoAleatorio);
		
		return agenciaGerada.toString();
	}
	
	private String criaNumeroConta() {
		Random numeroAleatorio = new Random(); 
		int contaAleatoria;
		int digitoAleatorio;
		
		contaAleatoria = numeroAleatorio.nextInt(9999999);
		digitoAleatorio = numeroAleatorio.nextInt(10);
		
		StringBuilder contaGerada = new StringBuilder();
		contaGerada.append(contaAleatoria);
		contaGerada.append("-");
		contaGerada.append(digitoAleatorio);
		
		return contaGerada.toString();
	}
	

}
