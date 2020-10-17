package com.digital.banco.nosso.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.digital.banco.nosso.domain.model.Endereco;

@Repository
public interface EnderecoRepository extends CustomJpaRepository<Endereco, Long>, JpaSpecificationExecutor<Endereco> {

	@Query("from Endereco e")
	List<Endereco> findAll();

}