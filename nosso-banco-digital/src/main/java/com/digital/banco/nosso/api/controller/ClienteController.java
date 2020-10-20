
package com.digital.banco.nosso.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import com.digital.banco.nosso.api.openapi.controller.ClienteControllerOpenApi;
import com.digital.banco.nosso.domain.exception.ClienteNaoEncontradoException;
import com.digital.banco.nosso.domain.exception.NegocioException;
import com.digital.banco.nosso.domain.model.Cliente;
import com.digital.banco.nosso.domain.service.CadastroClienteService;

@RestController
@RequestMapping(value = "/clientes", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClienteController implements ClienteControllerOpenApi {

	@Autowired
	private CadastroClienteService cadastroCliente;

	@Autowired
	private ClienteModelAssembler clienteModelAssembler;

	@Autowired
	private ClienteComEnderecoModelAssembler clienteComEnderecoModelAssembler;

	@Autowired
	private ClienteInputDisassembler clienteInputDisassembler;

	@GetMapping("/{cpfCliente}")
	public ClienteComEnderecoModel buscar(@PathVariable String cpfCliente) {
		Cliente cliente = cadastroCliente.buscarOuFalharCpf(cpfCliente);
 
		return clienteComEnderecoModelAssembler.toModel(cliente);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<ClienteModel> adicionar(@RequestBody @Valid ClienteInput clienteInput) {
		try {

			Cliente cliente = clienteInputDisassembler.toDomainObject(clienteInput);

			ClienteModel clienteModel = clienteModelAssembler.toModel(cadastroCliente.salvar(cliente));

			return ResponseEntity.ok()
					.header("Location", 
							"http://localhost:8080/enderecos/" 
					+ clienteModel.getCpf())
					.body(clienteModel);
		} catch (ClienteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

}
