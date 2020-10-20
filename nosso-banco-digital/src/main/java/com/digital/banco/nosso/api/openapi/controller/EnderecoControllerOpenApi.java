package com.digital.banco.nosso.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.digital.banco.nosso.api.exceptionHandler.Problem;
import com.digital.banco.nosso.api.model.EnderecoModel;
import com.digital.banco.nosso.api.model.input.EnderecoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Enderecos")
public interface EnderecoControllerOpenApi {
	
	@ApiOperation("Listagem de Enderecos")
	public CollectionModel<EnderecoModel> listar() ;

	@ApiOperation("Busca Endereço Específico Por ID")
	@ApiResponses({
		@ApiResponse(code=404, message = "Endereço não encontrado", response = Problem.class)
	})
	public EnderecoModel buscar(@ApiParam(value="ID do Endereço", example = "1") Long enderecoId) ;

	@ApiOperation("Cadastra Endereço")
	public ResponseEntity<EnderecoModel> adicionar(@ApiParam(name="corpo", value = "Representação de um endereço") 
	EnderecoInput enderecoInput, String clienteCpf) ;

	
}
