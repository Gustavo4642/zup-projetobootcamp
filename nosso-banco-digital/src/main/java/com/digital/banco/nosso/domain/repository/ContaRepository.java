package com.digital.banco.nosso.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.digital.banco.nosso.domain.model.Conta;

@Repository
public interface ContaRepository extends CustomJpaRepository<Conta, Long>, JpaSpecificationExecutor<Conta>{

	@Query("from Proposta p ")
	List<Conta> findAll();
	
	@Query("from Conta c "
			+ "join c.proposta p "
			+ "where c.codigo = :codigoConta")
	Conta procureContaProposta(String codigoConta);
	
}