package com.digital.banco.nosso.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.digital.banco.nosso.api.controller.ClienteController;
import com.digital.banco.nosso.api.model.ClienteModel;
import com.digital.banco.nosso.domain.model.Cliente;

@Component
public class ClienteModelAssembler extends RepresentationModelAssemblerSupport<Cliente, ClienteModel> {

	@Autowired
	private ModelMapper modelMapper;

	public ClienteModelAssembler() {
		super(ClienteController.class, ClienteModel.class);
	}

	@Override
	public ClienteModel toModel(Cliente cliente) {
	
		ClienteModel clienteModel = createModelWithId(cliente.getId(), cliente);
		modelMapper.map(cliente, clienteModel);

		return clienteModel;
	}
	
	@Override
	public CollectionModel<ClienteModel> toCollectionModel(Iterable<? extends Cliente> entities) {
		return super.toCollectionModel(entities)
			.add(linkTo(ClienteController.class).withSelfRel());
	}

}
