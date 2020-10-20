package com.digital.banco.nosso.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.digital.banco.nosso.api.ZupLinks;
import com.digital.banco.nosso.api.controller.ClienteController;
import com.digital.banco.nosso.api.controller.PropostaController;
import com.digital.banco.nosso.api.model.PropostaModel;
import com.digital.banco.nosso.domain.model.Proposta;

@Component
public class PropostaModelAssembler extends RepresentationModelAssemblerSupport<Proposta, PropostaModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ZupLinks zupLinks;

	public PropostaModelAssembler() {
		super(PropostaController.class, PropostaModel.class);
	}

	@Override
	public PropostaModel toModel(Proposta proposta) {
		PropostaModel propostaModel = createModelWithId(proposta.getId(), proposta);

		modelMapper.map(proposta, propostaModel);

		if (proposta.podeSerConfirmado()) {
			propostaModel.add(zupLinks.linkParaAtivarProposta(propostaModel.getCliente().getCpf(), "ativar"));
		}

		if (proposta.podeSerRecusado()) {
			propostaModel.add(zupLinks.linkParaRecusarProposta(propostaModel.getCliente().getCpf(), "recusar"));
		}

		if (proposta.podeSerInativado()) {
			propostaModel.add(zupLinks.linkParaInativarProposta(propostaModel.getCliente().getCpf(), "inativar"));
		}
		return propostaModel;
	}

	@Override
	public CollectionModel<PropostaModel> toCollectionModel(Iterable<? extends Proposta> entities) {
		return super.toCollectionModel(entities).add(linkTo(ClienteController.class).withSelfRel());
	}

}
