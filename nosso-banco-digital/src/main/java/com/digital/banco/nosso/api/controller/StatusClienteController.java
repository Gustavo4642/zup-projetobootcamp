package com.digital.banco.nosso.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digital.banco.nosso.api.assembler.ContaModelAssembler;
import com.digital.banco.nosso.api.assembler.PropostaModelAssembler;
import com.digital.banco.nosso.api.model.ContaModel;
import com.digital.banco.nosso.api.model.PropostaModel;
import com.digital.banco.nosso.domain.exception.NegocioException;
import com.digital.banco.nosso.domain.model.Cliente;
import com.digital.banco.nosso.domain.model.Conta;
import com.digital.banco.nosso.domain.model.FotoCliente;
import com.digital.banco.nosso.domain.model.Proposta;
import com.digital.banco.nosso.domain.service.AlteraStatusClienteService;
import com.digital.banco.nosso.domain.service.CadastroClienteService;
import com.digital.banco.nosso.domain.service.CadastroContaService;
import com.digital.banco.nosso.domain.service.CadastroFotoClienteService;
import com.digital.banco.nosso.domain.service.CadastroPropostaService;
import com.digital.banco.nosso.domain.service.FotoStorageService;
import com.digital.banco.nosso.domain.service.FotoStorageService.FotoRecuperada;

@RestController
@RequestMapping(value = "/clientes/{clienteCpf}")
public class StatusClienteController {

	@Autowired
	private CadastroClienteService cadastroCliente;

	@Autowired
	private CadastroFotoClienteService cadastroFoto;

	@Autowired
	private CadastroPropostaService cadastroProposta;

	@Autowired
	private CadastroContaService cadastroConta;

	@Autowired
	private AlteraStatusClienteService alteraStatusCliente;

	@Autowired
	private PropostaModelAssembler propostaModelAssembler;

	@Autowired
	private ContaModelAssembler contaModelAssembler;

	@Autowired
	private FotoStorageService fotoStorage;

	@PutMapping("/ativar")
	public ResponseEntity<ContaModel> ativar(@PathVariable String clienteCpf) {

		try {
			Cliente cliente = cadastroCliente.buscarOuFalharCpf(clienteCpf);

			Conta conta = cadastroConta.criaConta(cliente);

			alteraStatusCliente.ativarCliente(cliente, conta);

			ContaModel contaModel = contaModelAssembler.toModel(conta);

			return ResponseEntity.status(HttpStatus.OK).body(contaModel);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}

	}

	@PutMapping("/analisar")
	public ResponseEntity<PropostaModel> analisar(@PathVariable String clienteCpf) {

		try {
			Cliente cliente = cadastroCliente.buscarOuFalharCpf(clienteCpf);
			FotoCliente fotoCliente = cadastroFoto.buscarOuFalhar(cliente.getCodigo());
			FotoRecuperada fotoRecuperada = fotoStorage.recuperar(fotoCliente.getNomeArquivo());

			if (fotoRecuperada.temUrl()) {
				alteraStatusCliente.aguardandoAnaliseCliente(cliente, fotoRecuperada.getUrl());
			} else {
				throw new NegocioException(String.format("Cliente %s, CPF %s, não possui documento para avaliação",
						cliente.getNome(), cliente.getCpf()));
			}

			PropostaModel propostaModel = propostaModelAssembler.toModel(cadastroProposta.salvar(cliente));

			return ResponseEntity.status(HttpStatus.OK).body(propostaModel);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/inativar")
	public ResponseEntity<PropostaModel> inativar(@PathVariable String clienteCpf) {

		try {
			Proposta proposta = cadastroProposta.buscarPropostaNaoOptional(clienteCpf);

			if (proposta != null) {
				cadastroProposta.alteraStatus(proposta);
			}

			alteraStatusCliente.inativarCliente(clienteCpf);

			PropostaModel propostaModel = propostaModelAssembler.toModel(proposta);

			return ResponseEntity.status(HttpStatus.OK).body(propostaModel);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/recusar")
	public ResponseEntity<PropostaModel> recusar(@PathVariable String clienteCpf) {

		try {
			Proposta proposta = cadastroProposta.buscarPropostaNaoOptional(clienteCpf);

			if (proposta != null) {
				cadastroProposta.alteraStatus(proposta);
			}

			alteraStatusCliente.recusadoPeloCliente(clienteCpf);

			PropostaModel propostaModel = propostaModelAssembler.toModel(proposta);

			return ResponseEntity.status(HttpStatus.OK).body(propostaModel);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}

	}

}
