package com.digital.banco.nosso.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class FotoCliente {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cli_id")
	private Long id;
	
	@Column(name = "fot_nome_arquivo", nullable = false)
	private String nomeArquivo;
	
	@Column(name = "fot_descricao")
	private String descricao;
	
	@Column(name = "fot_content_type", nullable = false)
	private String contentType;
	
	@Column(name = "fot_tamanho", nullable = false)
	private Long tamanho;
	
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	private Cliente cli;
	
	public String getCliId() {
		
		if(getCli() != null) {
			return getCli().getCodigo();
		}
		
		return null;
	}
	
}
