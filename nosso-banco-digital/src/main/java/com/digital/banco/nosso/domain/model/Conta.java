package com.digital.banco.nosso.domain.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Conta {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "con_agencia", nullable = false)
	private String agencia;
	
	@Column(name = "con_numero_conta", nullable = false)
	private String numeroConta;
	
	@Column(name = "con_codigo_banco", nullable = false)
	private String codigoBanco;
	
	@Column(name = "con_saldo", nullable = false)
	private BigDecimal saldo;	
	
	@OneToMany(mappedBy = "conta", cascade = CascadeType.ALL)
	private Proposta proposta;
	//private Movimentacao movimentacao;
}
