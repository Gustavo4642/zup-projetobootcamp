package com.digital.banco.nosso.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digital.banco.nosso.api.assembler.ClienteExibicaoModelAssembler;
import com.digital.banco.nosso.api.model.ClienteExibicaoModel;
import com.digital.banco.nosso.domain.model.Cliente;
import com.digital.banco.nosso.domain.model.FotoCliente;
import com.digital.banco.nosso.domain.service.CadastroClienteService;
import com.digital.banco.nosso.domain.service.CadastroFotoClienteService;
import com.digital.banco.nosso.domain.service.FotoStorageService;
import com.digital.banco.nosso.domain.service.FotoStorageService.FotoRecuperada;

@RestController
@RequestMapping(value = "/exibirCliente")
public class ExibirClienteCompletoController {

	@Autowired
	private CadastroClienteService cadastroCliente;

	@Autowired
	private CadastroFotoClienteService cadastroFoto;
	
	@Autowired
	private ClienteExibicaoModelAssembler clienteExibicaoModelAssembler;
	
	@Autowired
	private FotoStorageService fotoStorage;
	
	@GetMapping("/{cpfCliente}")
	public ClienteExibicaoModel buscar(@PathVariable String cpfCliente) {
		Cliente cliente = cadastroCliente.buscarOuFalharCpf(cpfCliente);
		
		FotoCliente fotoCliente = cadastroFoto.buscarOuFalhar(cliente.getCodigo());
		
		FotoRecuperada fotoRecuperada = fotoStorage.recuperar(fotoCliente.getNomeArquivo());
		
		ClienteExibicaoModel clienteExibicao = clienteExibicaoModelAssembler.toModel(cliente);
		
		if(fotoRecuperada.temUrl()) {
			clienteExibicao.setUrlImagem(fotoRecuperada.getUrl());
		} else {
			clienteExibicao.setUrlImagem("Gravado em diretório local.");
		}
		
		return clienteExibicao;
	}
	
}


