
package com.digital.banco.nosso.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digital.banco.nosso.api.assembler.ContaModelAssembler;
import com.digital.banco.nosso.api.model.ContaModel;
import com.digital.banco.nosso.domain.repository.ContaRepository;

@RestController
@RequestMapping(value = "/contas")
public class ContaController {
	
	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private ContaModelAssembler contaModelAssembler;
	
	@GetMapping
	public List<ContaModel> listar() {
		return contaModelAssembler.toCollectionModel(contaRepository.findAll());
	}
	
}


