package com.digital.banco.nosso.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.digital.banco.nosso.api.controller.ClienteController;
import com.digital.banco.nosso.api.controller.ContaController;
import com.digital.banco.nosso.api.controller.PropostaController;
import com.digital.banco.nosso.api.model.ContaModel;
import com.digital.banco.nosso.domain.model.Conta;

@Component
public class ContaModelAssembler extends RepresentationModelAssemblerSupport<Conta, ContaModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	public ContaModelAssembler() {
		super(ContaController.class, ContaModel.class);
	}

	@Override
	public ContaModel toModel(Conta conta) {
	
		ContaModel contaModel = createModelWithId(conta.getId(), conta);
		
		modelMapper.map(conta, contaModel);

		contaModel.add(linkTo(methodOn(PropostaController.class)
				.buscar(contaModel.getProposta().getCodigo())).withSelfRel());
		
		return contaModel;
	}
	
	@Override
	public CollectionModel<ContaModel> toCollectionModel(Iterable<? extends Conta> entities) {
		return super.toCollectionModel(entities)
			.add(linkTo(ClienteController.class).withSelfRel());
	}
}
