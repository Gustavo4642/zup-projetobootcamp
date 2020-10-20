package com.digital.banco.nosso.api.openapi.controller;

import org.springframework.http.ResponseEntity;

import com.digital.banco.nosso.api.exceptionHandler.Problem;
import com.digital.banco.nosso.api.model.ClienteComEnderecoModel;
import com.digital.banco.nosso.api.model.ClienteModel;
import com.digital.banco.nosso.api.model.input.ClienteInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Clientes")
public interface ClienteControllerOpenApi {
	
	@ApiOperation("Busca Cliente Específico Por CPF")
	@ApiResponses({
		@ApiResponse(code=404, message = "Cliente não encontrado", response = Problem.class)
	})
	public ClienteComEnderecoModel buscar(@ApiParam(value="CPF do Cliente", example = "999.999.999-99") String cpfCliente) ;

	@ApiOperation("Cadastra Cliente")
	public ResponseEntity<ClienteModel> adicionar(@ApiParam(name="corpo", value = "Representação de um novo cliente") ClienteInput clienteInput) ;
	
}
