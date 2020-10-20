package com.digital.banco.nosso.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.digital.banco.nosso.api.exceptionHandler.Problem;
import com.digital.banco.nosso.api.model.PropostaModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Propostas")
public interface PropostaControllerOpenApi {
	
	@ApiOperation("Listagem de Propostas")
	public CollectionModel<PropostaModel> listar() ;

	@ApiOperation("Busca uma proposta específica pelo código da proposta")
	@ApiResponses({
		@ApiResponse(code=404, message = "Proposta não encontrado", response = Problem.class)
	})
	public ResponseEntity<PropostaModel> buscar(@ApiParam(value="Código de 36 digitos") String codigoProposta) ;
}
