
package com.digital.banco.nosso.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digital.banco.nosso.api.assembler.PropostaModelAssembler;
import com.digital.banco.nosso.api.model.PropostaModel;
import com.digital.banco.nosso.api.openapi.controller.PropostaControllerOpenApi;
import com.digital.banco.nosso.domain.model.Proposta;
import com.digital.banco.nosso.domain.repository.PropostaRepository;
import com.digital.banco.nosso.domain.service.CadastroPropostaService;

@RestController
@RequestMapping(value = "/propostas", produces = MediaType.APPLICATION_JSON_VALUE)
public class PropostaController implements PropostaControllerOpenApi {

	@Autowired
	private PropostaRepository propostaRepository;

	@Autowired
	private PropostaModelAssembler propostaModelAssembler;

	@Autowired
	private CadastroPropostaService cadastroProposta;

	@GetMapping
	public CollectionModel<PropostaModel> listar() {
		List<Proposta> propostas = propostaRepository.findAll();
		
		return propostaModelAssembler.toCollectionModel(propostas);
	}

	@GetMapping("/{codigoProposta}")
	public ResponseEntity<PropostaModel> buscar(@PathVariable String codigoProposta) {
		
		try {
			Proposta proposta = cadastroProposta.buscarPropostaPorCodigo(codigoProposta);

			PropostaModel propostaModel = propostaModelAssembler.toModel(proposta);
			
			switch (proposta.getStatusProposta().getDescricao()) {
			case "Aceita":
				return ResponseEntity.ok(propostaModel);
			case "Aguardando":
				return ResponseEntity.status(HttpStatus.CONFLICT).body(propostaModel);
			case "Rejeitada":
				return ResponseEntity.ok(propostaModel);
			default:
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
	}
}
