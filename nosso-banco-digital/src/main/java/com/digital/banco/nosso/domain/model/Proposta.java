package com.digital.banco.nosso.domain.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Proposta {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "pro_codigo", nullable = false)
	private String codigo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pro_cliente_id", nullable = false)
	private Cliente cliente;
	
	@OneToOne(mappedBy = "proposta")
	private Conta conta;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "pro_status", nullable = false)
	private StatusProposta statusProposta = StatusProposta.AGUARDANDO;
	
	@Column(name = "pro_motivo")
	private String motivo;
	
	@PrePersist 
	private void gerarCodigo() {
		setCodigo(UUID.randomUUID().toString());
	}	
	
	public boolean podeSerConfirmado() {
		return getStatusProposta() == StatusProposta.AGUARDANDO;
	}
	
	public boolean podeSerRecusado() {
		return getStatusProposta() == StatusProposta.AGUARDANDO;
	}
	
	public boolean podeSerInativado() {
		return getStatusProposta() == StatusProposta.AGUARDANDO;
	}
}
