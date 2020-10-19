package com.digital.banco.nosso.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
public class Conta {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "con_codigo", nullable = false)
	private String codigo;
	
	@Column(name = "con_agencia", nullable = false)
	private String agencia;
	
	@Column(name = "con_numero_conta", nullable = false)
	private String numeroConta;
	
	@Column(name = "con_codigo_banco", nullable = false)
	private String codigoBanco;
	
	@Column(name = "con_saldo", nullable = false)
	private BigDecimal saldo;	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "con_proposta_id", nullable = false)
	private Proposta proposta;
	//private Movimentacao movimentacao;
	
	@PrePersist 
	private void gerarCodigo() {
		setCodigo(UUID.randomUUID().toString());
	}	
}
