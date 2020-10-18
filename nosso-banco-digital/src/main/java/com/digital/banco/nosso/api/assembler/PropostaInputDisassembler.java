package com.digital.banco.nosso.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.digital.banco.nosso.api.model.input.PropostaInput;
import com.digital.banco.nosso.domain.model.Proposta;

@Component
public class PropostaInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Proposta toDomainObject(PropostaInput propostaInput) {
		return modelMapper.map(propostaInput, Proposta.class);
	}
		
	public void copyToDomainObject(PropostaInput propostaInput, Proposta proposta) {
		modelMapper.map(propostaInput, proposta);
	}
}
