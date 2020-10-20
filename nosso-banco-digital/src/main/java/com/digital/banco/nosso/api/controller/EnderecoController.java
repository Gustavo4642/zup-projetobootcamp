package com.digital.banco.nosso.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.digital.banco.nosso.api.ResourceUriHelper;
import com.digital.banco.nosso.api.assembler.EnderecoInputDisassembler;
import com.digital.banco.nosso.api.assembler.EnderecoModelAssembler;
import com.digital.banco.nosso.api.model.EnderecoModel;
import com.digital.banco.nosso.api.model.input.EnderecoInput;
import com.digital.banco.nosso.api.openapi.controller.EnderecoControllerOpenApi;
import com.digital.banco.nosso.domain.exception.ClienteNaoEncontradoException;
import com.digital.banco.nosso.domain.exception.EnderecoNaoEncontradoException;
import com.digital.banco.nosso.domain.exception.NegocioException;
import com.digital.banco.nosso.domain.model.Cliente;
import com.digital.banco.nosso.domain.model.Endereco;
import com.digital.banco.nosso.domain.repository.EnderecoRepository;
import com.digital.banco.nosso.domain.service.CadastroClienteService;
import com.digital.banco.nosso.domain.service.CadastroEnderecoService;

@RestController
@RequestMapping(value = "/enderecos", produces = MediaType.APPLICATION_JSON_VALUE)
public class EnderecoController implements EnderecoControllerOpenApi{

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private CadastroEnderecoService cadastroEndereco;

	@Autowired
	private CadastroClienteService cadastroCliente;

	@Autowired
	private EnderecoModelAssembler enderecoModelAssembler;

	@Autowired
	private EnderecoInputDisassembler enderecoInputDisassembler;
	
	@GetMapping
	public CollectionModel<EnderecoModel> listar() {
		List<Endereco> enderecos = enderecoRepository.findAll();
		
		return enderecoModelAssembler.toCollectionModel(enderecos);
	}

	@GetMapping("/{enderecoId}")
	public EnderecoModel buscar(@PathVariable Long enderecoId) {
		Endereco endereco = cadastroEndereco.buscarOuFalhar(enderecoId);

		return enderecoModelAssembler.toModel(endereco);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EnderecoModel adicionar(@RequestBody @Valid EnderecoInput enderecoInput) {
		try {

			Cliente cliente = cadastroCliente.buscarOuFalharCpf(enderecoInput.getCpf());
			Endereco endereco = enderecoInputDisassembler.toDomainObject(enderecoInput);
			endereco.setCliente(cliente);

			EnderecoModel enderecoModel = enderecoModelAssembler.toModel(cadastroEndereco.salvar(endereco));
			
			ResourceUriHelper.addUriInResponseHeader(enderecoModel.getId());
			
			return enderecoModel;
		} catch(ClienteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		} catch (EnderecoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

}
