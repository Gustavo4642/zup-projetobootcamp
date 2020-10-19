
package com.digital.banco.nosso.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digital.banco.nosso.api.assembler.PropostaModelAssembler;
import com.digital.banco.nosso.api.model.PropostaModel;
import com.digital.banco.nosso.domain.exception.PropostaNaoEncontradaException;
import com.digital.banco.nosso.domain.model.Proposta;
import com.digital.banco.nosso.domain.repository.PropostaRepository;
import com.digital.banco.nosso.domain.service.CadastroPropostaService;

@RestController
@RequestMapping(value = "/propostas")
public class PropostaController {
	
	@Autowired
	private PropostaRepository propostaRepository;

	@Autowired
	private PropostaModelAssembler propostaModelAssembler;
	
	@Autowired
	private CadastroPropostaService cadastroProposta;
	
	@GetMapping
	public List<PropostaModel> listar() {
		return propostaModelAssembler.toCollectionModel(propostaRepository.findAll());
	}

	@GetMapping("/{cpfCliente}")
	public PropostaModel buscar(@PathVariable String cpfCliente) {
		Proposta proposta = cadastroProposta.buscarPropostaNaoOptional(cpfCliente);
		
		if(proposta != null) {
			return propostaModelAssembler.toModel(proposta);	
		} else {
			throw new PropostaNaoEncontradaException(cpfCliente);
		}
	}
	
}


