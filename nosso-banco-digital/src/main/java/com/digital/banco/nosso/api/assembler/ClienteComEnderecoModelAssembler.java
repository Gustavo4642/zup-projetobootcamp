package com.digital.banco.nosso.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.digital.banco.nosso.api.model.ClienteComEnderecoModel;
import com.digital.banco.nosso.domain.model.Cliente;

@Component
public class ClienteComEnderecoModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public ClienteComEnderecoModel toModel(Cliente cliente) {
		return modelMapper.map(cliente, ClienteComEnderecoModel.class);
	}
	
	public List<ClienteComEnderecoModel> toCollectionModel(List<Cliente> clientes) {
		return clientes.stream()
				.map(cliente -> toModel(cliente))
				.collect(Collectors.toList());
	}
	
}
