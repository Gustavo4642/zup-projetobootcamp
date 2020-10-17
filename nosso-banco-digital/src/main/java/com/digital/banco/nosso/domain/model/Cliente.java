package com.digital.banco.nosso.domain.model;

import java.time.OffsetDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cliente {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "cli_codigo", nullable = false)
	private String codigo;
	
	@Column(name = "cli_nome", nullable = false)
	private String nome;
	
	@Column(name = "cli_sobrenome", nullable = false)
	private String sobrenome;
	
	@Column(name = "cli_email", nullable = false)
	private String email;
	
	@Column(name = "cli_data_nascimento", nullable = false)
	private OffsetDateTime dataNascimento;
	
	@Column(name = "cli_cpf", nullable = false)
	private String cpf;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cli_endereco_id")
	private Endereco endereco;
	
	// para gerar um código de cliente aleatório
	@PrePersist // antes de executar uma persistencia, executa esse método
	private void gerarCodigo() {
		setCodigo(UUID.randomUUID().toString());
	}
	
}
