package com.digital.banco.nosso.domain.repository;

import org.springframework.stereotype.Repository;

import com.digital.banco.nosso.domain.model.FotoCliente;

@Repository
public interface ClienteRepositoryQueries {

	FotoCliente save(FotoCliente foto);
	
	void delete(FotoCliente foto);
}