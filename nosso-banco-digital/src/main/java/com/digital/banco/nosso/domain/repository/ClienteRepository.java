package com.digital.banco.nosso.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.digital.banco.nosso.domain.model.Cliente;
import com.digital.banco.nosso.domain.model.FotoCliente;

@Repository
public interface ClienteRepository extends CustomJpaRepository<Cliente, Long>, JpaSpecificationExecutor<Cliente>, ClienteRepositoryQueries {

	@Query("from Cliente c ")
	List<Cliente> findAll();

	@Query("from Cliente c where c.email = :email")
	Cliente procurePorEmail(@Param("email") String email);
	
	@Query("from Cliente c where c.cpf = :cpf")
	Cliente procurePorCpf(@Param("cpf") String cpf);

	@Query("from Cliente c "
			+ "join c.endereco e "
			+ "where c.cpf = :cpfCliente")
	Cliente procurePorCpfComEndereco(String cpfCliente);
	
	@Query("from Cliente c "
			+ "where c.cpf = :cpfCliente")
	Optional<Cliente> findByCpf(String cpfCliente);
	
	// FOTOS
	
	@Query("select f from FotoCliente f "
			+ "join f.cli c "
			+ "where c.codigo = :codigoCliente")
	Optional<FotoCliente> findFotoById(String codigoCliente);
	
	@Query("select f from FotoCliente f "
			+ "join f.cli c "
			+ "where c.cpf = :cpfCliente")
	FotoCliente findFotoByCpf(String cpfCliente);
	
}