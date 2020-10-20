
package com.digital.banco.nosso.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digital.banco.nosso.api.assembler.ContaModelAssembler;
import com.digital.banco.nosso.api.assembler.ContaPropostaModelAssembler;
import com.digital.banco.nosso.api.model.ContaModel;
import com.digital.banco.nosso.api.model.ContaPropostaModel;
import com.digital.banco.nosso.domain.model.Conta;
import com.digital.banco.nosso.domain.repository.ContaRepository;

@RestController
@RequestMapping(value = "/contas")
public class ContaController {
	
	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private ContaModelAssembler contaModelAssembler;
	
	@Autowired
	private ContaPropostaModelAssembler contaPropostaModelAssembler;
	
	@GetMapping
	public CollectionModel<ContaModel> listar() {
		List<Conta> contas = contaRepository.findAll();
		
		return contaModelAssembler.toCollectionModel(contas);
	}
	
	@GetMapping("/{codigoConta}")
	public ContaPropostaModel buscar(@PathVariable String codigoConta) {
		Conta conta = contaRepository.procureContaProposta(codigoConta);
 
		return contaPropostaModelAssembler.toModel(conta);
	}
	
}


