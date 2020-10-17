package com.digital.banco.nosso.infrastructure.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.digital.banco.nosso.domain.model.FotoCliente;
import com.digital.banco.nosso.domain.repository.ClienteRepositoryQueries;

@Repository
public class ClienteRepositoryImpl implements ClienteRepositoryQueries{

	@PersistenceContext
	private EntityManager manager;

	@Transactional
	@Override
	public FotoCliente save(FotoCliente foto) {
		return manager.merge(foto);
	}

	@Transactional
	@Override
	public void delete(FotoCliente foto) {
		manager.remove(foto);
	}
	
	
}
