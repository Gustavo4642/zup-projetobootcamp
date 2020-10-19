package com.digital.banco.nosso.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.digital.banco.nosso.domain.model.Proposta;

@Repository
public interface PropostaRepository extends CustomJpaRepository<Proposta, Long>, JpaSpecificationExecutor<Proposta>{

	@Query("from Proposta p ")
	List<Proposta> findAll();
	
	@Query("from Proposta p "
			+ "join p.cliente c "
			+ "where c.cpf = :cpfCliente "
			+ "and p.statusProposta != 'REJEITADA'")
	Optional<Proposta> findByCpfCliente(String cpfCliente);
	
	@Query("from Proposta p "
			+ "join p.cliente c "
			+ "where c.cpf = :cpfCliente "
			+ "and p.statusProposta != 'REJEITADA'")
	Proposta findByCpfNaoOptional(String cpfCliente);	
	
	@Query("from Proposta p "
			+ "join p.cliente c "
			+ "where p.codigo = :codigoProposta ")
	Proposta buscarPropostaPorCodigo(String codigoProposta);	

	
}