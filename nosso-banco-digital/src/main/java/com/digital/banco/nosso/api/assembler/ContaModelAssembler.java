package com.digital.banco.nosso.api.assembler;

import java.util.List; 
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.digital.banco.nosso.api.model.ContaModel;
import com.digital.banco.nosso.domain.model.Conta;

@Component
public class ContaModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public ContaModel toModel(Conta conta) {
		return modelMapper.map(conta, ContaModel.class);
	}
	
	public List<ContaModel> toCollectionModel(List<Conta> contas) {
		return contas.stream()
				.map(conta -> toModel(conta))
				.collect(Collectors.toList());
	}
	
}
