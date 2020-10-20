package com.digital.banco.nosso.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.digital.banco.nosso.api.controller.ClienteController;
import com.digital.banco.nosso.api.controller.EnderecoController;
import com.digital.banco.nosso.api.model.ClienteComEnderecoModel;
import com.digital.banco.nosso.domain.model.Cliente;

@Component
public class ClienteComEnderecoModelAssembler extends RepresentationModelAssemblerSupport<Cliente, ClienteComEnderecoModel> {

	@Autowired
	private ModelMapper modelMapper;

	public ClienteComEnderecoModelAssembler() {
		super(ClienteController.class, ClienteComEnderecoModel.class);
	}
	
	public ClienteComEnderecoModel toModel(Cliente cliente) {
		
		ClienteComEnderecoModel clienteComEnderecoModel = createModelWithId(cliente.getId(), cliente);
		
		modelMapper.map(cliente, clienteComEnderecoModel);

		clienteComEnderecoModel.add(linkTo(methodOn(EnderecoController.class)
				.buscar(clienteComEnderecoModel.getEndereco().getId())).withSelfRel());
		
		return clienteComEnderecoModel;
	}
	
	@Override
	public CollectionModel<ClienteComEnderecoModel> toCollectionModel(Iterable<? extends Cliente> entities) {
		return super.toCollectionModel(entities)
			.add(linkTo(ClienteController.class).withSelfRel());
	}
	
}
