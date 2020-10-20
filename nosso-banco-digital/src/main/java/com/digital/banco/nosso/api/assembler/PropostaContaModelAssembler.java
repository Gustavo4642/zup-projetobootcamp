package com.digital.banco.nosso.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.digital.banco.nosso.api.controller.ClienteController;
import com.digital.banco.nosso.api.controller.PropostaController;
import com.digital.banco.nosso.api.model.PropostaContaModel;
import com.digital.banco.nosso.domain.model.Proposta;

@Component
public class PropostaContaModelAssembler extends RepresentationModelAssemblerSupport<Proposta, PropostaContaModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	public PropostaContaModelAssembler() {
		super(PropostaController.class, PropostaContaModel.class);
	}

	@Override
	public PropostaContaModel toModel(Proposta proposta) {
		PropostaContaModel propostaContaModel = createModelWithId(proposta.getId(), proposta);
		
		modelMapper.map(proposta, propostaContaModel);
		
		return propostaContaModel;
	}
	
	@Override
	public CollectionModel<PropostaContaModel> toCollectionModel(Iterable<? extends Proposta> entities) {
		return super.toCollectionModel(entities)
			.add(linkTo(ClienteController.class).withSelfRel());
	}

	
}
