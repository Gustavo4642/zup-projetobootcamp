
package com.digital.banco.nosso.api.controller;

import java.util.List; 

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.digital.banco.nosso.api.assembler.ClienteComEnderecoModelAssembler;
import com.digital.banco.nosso.api.assembler.ClienteInputDisassembler;
import com.digital.banco.nosso.api.assembler.ClienteModelAssembler;
import com.digital.banco.nosso.api.model.ClienteComEnderecoModel;
import com.digital.banco.nosso.api.model.ClienteModel;
import com.digital.banco.nosso.api.model.input.ClienteInput;
import com.digital.banco.nosso.domain.exception.ClienteNaoEncontradoException;
import com.digital.banco.nosso.domain.exception.NegocioException;
import com.digital.banco.nosso.domain.model.Cliente;
import com.digital.banco.nosso.domain.repository.ClienteRepository;
import com.digital.banco.nosso.domain.service.CadastroClienteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Clientes")
@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private CadastroClienteService cadastroCliente;

	@Autowired
	private ClienteModelAssembler clienteModelAssembler;
	
	@Autowired
	private ClienteComEnderecoModelAssembler clienteComEnderecoModelAssembler;
	
	@Autowired
	private ClienteInputDisassembler clienteInputDisassembler;
	
	@ApiOperation("Listagem de Clientes")
	@GetMapping
	public List<ClienteModel> listar() {
		return clienteModelAssembler.toCollectionModel(clienteRepository.findAll());
	}

	@ApiOperation("Busca Cliente Específico Por CPF")
	@GetMapping("/{cpfCliente}")
	public ClienteComEnderecoModel buscar(@ApiParam(value="CPF do Cliente", example = "999.999.999-99") 
		@PathVariable String cpfCliente) {
		Cliente cliente = cadastroCliente.buscarOuFalharCpf(cpfCliente);
		
		return clienteComEnderecoModelAssembler.toModel(cliente);	
	}

	@ApiOperation("Cadastra Cliente")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ClienteModel adicionar(@ApiParam(name="corpo", value = "Representação de um novo cliente")
			@RequestBody @Valid ClienteInput clienteInput) {
		try {
			
			Cliente cliente = clienteInputDisassembler.toDomainObject(clienteInput);
			
			return clienteModelAssembler.toModel(cadastroCliente.salvar(cliente));
		} catch (ClienteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		} 
	}
	
}


