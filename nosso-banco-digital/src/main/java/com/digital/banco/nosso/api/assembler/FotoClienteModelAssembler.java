package com.digital.banco.nosso.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.digital.banco.nosso.api.model.FotoClienteModel;
import com.digital.banco.nosso.domain.model.FotoCliente;

@Component
public class FotoClienteModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public FotoClienteModel toModel(FotoCliente foto) {
		return modelMapper.map(foto, FotoClienteModel.class);
	}
}