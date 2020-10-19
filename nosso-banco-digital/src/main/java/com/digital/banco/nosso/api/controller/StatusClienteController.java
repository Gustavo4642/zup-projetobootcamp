package com.digital.banco.nosso.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.digital.banco.nosso.domain.exception.NegocioException;
import com.digital.banco.nosso.domain.model.Cliente;
import com.digital.banco.nosso.domain.model.FotoCliente;
import com.digital.banco.nosso.domain.model.Proposta;
import com.digital.banco.nosso.domain.service.AlteraStatusClienteService;
import com.digital.banco.nosso.domain.service.CadastroClienteService;
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
	private AlteraStatusClienteService alteraStatusCliente;

	@Autowired
	private FotoStorageService fotoStorage;

	// feito em propostaController
	/*@GetMapping("/consultarStatus")
	public ResponseEntity<ClienteModel> consulta(@PathVariable String clienteCpf) {
		Cliente cliente = cadastroCliente.buscarOuFalharCpf(clienteCpf);

		switch (cliente.getStatus().getDescricao()) {
		case "Ativo":
			return ResponseEntity.status(HttpStatus.OK).build();
		case "Inativo":
			return ResponseEntity.status(HttpStatus.OK).build();
		case "Análise":
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		case "Recusa":
			return ResponseEntity.status(HttpStatus.OK).build();
		default:
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

		}
	}*/

	@PutMapping("/ativar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable String clienteCpf) {
		
		alteraStatusCliente.ativarCliente(clienteCpf);
	}

	@PutMapping("/analisar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void analisar(@PathVariable String clienteCpf) {

		Cliente cliente = cadastroCliente.buscarOuFalharCpf(clienteCpf);
		FotoCliente fotoCliente = cadastroFoto.buscarOuFalhar(cliente.getCodigo());
		FotoRecuperada fotoRecuperada = fotoStorage.recuperar(fotoCliente.getNomeArquivo());

		if (fotoRecuperada.temUrl()) {
			alteraStatusCliente.aguardandoAnaliseCliente(cliente, fotoRecuperada.getUrl());
		} else {
			throw new NegocioException(String.format("Cliente %s, CPF %s, não possui documento para avaliação",
					cliente.getNome(), cliente.getCpf()));
		}

		cadastroProposta.salvar(cliente);
	}

	@PutMapping("/inativar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable String clienteCpf) {

		Proposta proposta = cadastroProposta.buscarPropostaNaoOptional(clienteCpf);

		if (proposta != null) {
			cadastroProposta.alteraStatus(proposta);
		}

		alteraStatusCliente.inativarCliente(clienteCpf);
	}

	@PutMapping("/recusar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void recusar(@PathVariable String clienteCpf) {

		Proposta proposta = cadastroProposta.buscarPropostaNaoOptional(clienteCpf);

		if (proposta != null) {
			cadastroProposta.alteraStatus(proposta);
		}

		alteraStatusCliente.recusadoPeloCliente(clienteCpf);
	}

}
