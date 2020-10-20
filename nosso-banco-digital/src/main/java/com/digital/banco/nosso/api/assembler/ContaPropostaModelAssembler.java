package com.digital.banco.nosso.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.digital.banco.nosso.api.controller.ClienteController;
import com.digital.banco.nosso.api.controller.ContaController;
import com.digital.banco.nosso.api.model.ContaPropostaModel;
import com.digital.banco.nosso.domain.model.Conta;

@Component
public class ContaPropostaModelAssembler extends RepresentationModelAssemblerSupport<Conta, ContaPropostaModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	public ContaPropostaModelAssembler() {
		super(ContaController.class, ContaPropostaModel.class);
	}

	@Override
	public ContaPropostaModel toModel(Conta conta) {
	
		ContaPropostaModel contaPropostaModel = createModelWithId(conta.getId(), conta);
		
		modelMapper.map(conta, contaPropostaModel);

		return contaPropostaModel;
	}
	
	@Override
	public CollectionModel<ContaPropostaModel> toCollectionModel(Iterable<? extends Conta> entities) {
		return super.toCollectionModel(entities)
			.add(linkTo(ClienteController.class).withSelfRel());
	}
}
