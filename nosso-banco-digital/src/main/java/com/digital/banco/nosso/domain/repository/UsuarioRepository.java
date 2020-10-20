package com.digital.banco.nosso.domain.repository;

import java.util.Optional; 

import org.springframework.stereotype.Repository;

import com.digital.banco.nosso.domain.model.Usuario;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {

	Optional<Usuario> findByEmail(String email);
	
}