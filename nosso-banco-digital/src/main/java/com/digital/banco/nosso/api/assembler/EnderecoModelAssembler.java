package com.digital.banco.nosso.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.digital.banco.nosso.api.controller.EnderecoController;
import com.digital.banco.nosso.api.model.EnderecoModel;
import com.digital.banco.nosso.domain.model.Endereco;

@Component
public class EnderecoModelAssembler extends RepresentationModelAssemblerSupport<Endereco, EnderecoModel> {

	@Autowired
	private ModelMapper modelMapper;

	public EnderecoModelAssembler() {
		super(EnderecoController.class, EnderecoModel.class);
	}

	@Override
	public EnderecoModel toModel(Endereco endereco) {
		EnderecoModel enderecoModel = createModelWithId(endereco.getId(), endereco);
		modelMapper.map(endereco, enderecoModel);

		return enderecoModel;
	}

	@Override
	public CollectionModel<EnderecoModel> toCollectionModel(Iterable<? extends Endereco> entities) {
		return super.toCollectionModel(entities).add(linkTo(EnderecoController.class).withSelfRel());
	}

}
