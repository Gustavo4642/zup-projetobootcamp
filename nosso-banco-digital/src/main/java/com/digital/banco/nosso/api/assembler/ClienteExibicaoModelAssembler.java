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
import com.digital.banco.nosso.api.model.ClienteExibicaoModel;
import com.digital.banco.nosso.domain.model.Cliente;

@Component
public class ClienteExibicaoModelAssembler extends RepresentationModelAssemblerSupport<Cliente, ClienteExibicaoModel>{
	
	@Autowired
	private ModelMapper modelMapper;
	
	public ClienteExibicaoModelAssembler() {
		super(ClienteController.class, ClienteExibicaoModel.class);
	}

	public ClienteExibicaoModel toModel(Cliente cliente) {
		ClienteExibicaoModel clienteExibicaoModel = createModelWithId(cliente.getId(), cliente);
		
		modelMapper.map(cliente, clienteExibicaoModel);
		
		clienteExibicaoModel.add(linkTo(methodOn(EnderecoController.class)
				.buscar(clienteExibicaoModel.getEndereco().getId())).withSelfRel());
		
		
		return clienteExibicaoModel;
	}
	
	@Override
	public CollectionModel<ClienteExibicaoModel> toCollectionModel(Iterable<? extends Cliente> entities) {
		return super.toCollectionModel(entities)
			.add(linkTo(ClienteController.class).withSelfRel());
	}

}
