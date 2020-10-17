package com.digital.banco.nosso.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Endereco {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "end_cep", nullable = false)
	private String cep;
	
	@Column(name = "end_rua", nullable = false)
	private String rua;
	
	@Column(name = "end_numero", nullable = false)
	private String numero;
	
	@Column(name = "end_bairro", nullable = false)
	private String bairro;
	
	@Column(name = "end_complemento", nullable = false)
	private String complemento;
	
	@Column(name = "end_cidade", nullable = false)
	private String cidade;
	
	@Column(name = "end_estado", nullable = false)
	private String estado;
	
	@OneToOne(mappedBy = "endereco")
	private Cliente cliente;
	
	
}
