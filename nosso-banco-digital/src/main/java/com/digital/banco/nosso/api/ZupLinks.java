package com.digital.banco.nosso.api;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.digital.banco.nosso.api.controller.StatusClienteController;

@Component
public class ZupLinks {

	public Link linkParaAtivarProposta(String cpfCliente, String rel) {
		return linkTo(methodOn(StatusClienteController.class)
				.ativar(cpfCliente))
				.withRel(rel);
	}
	
	public Link linkParaAnalisarProposta(String cpfCliente, String rel) {
		return linkTo(methodOn(StatusClienteController.class)
				.analisar(cpfCliente))
				.withRel(rel);
	}
	
	public Link linkParaInativarProposta(String cpfCliente, String rel) {
		return linkTo(methodOn(StatusClienteController.class)
				.inativar(cpfCliente))
				.withRel(rel);
	}
	
	public Link linkParaRecusarProposta(String cpfCliente, String rel) {
		return linkTo(methodOn(StatusClienteController.class)
				.recusar(cpfCliente))
				.withRel(rel);
	}
	
}
