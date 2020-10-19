package com.digital.banco.nosso.domain.service.mensagemEmail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digital.banco.nosso.domain.model.Cliente;
import com.digital.banco.nosso.domain.model.Conta;
import com.digital.banco.nosso.domain.service.EnvioEmailService;
import com.digital.banco.nosso.domain.service.EnvioEmailService.Mensagem;;

@Service
public class ConstroiMensagemEmail {

	@Autowired
	private EnvioEmailService envioEmailService;
	
	public void constroiMensagemClienteAnalise(Cliente cliente) {
		
		var mensagem = Mensagem.builder()
				.assunto("Análise do cliente: " + cliente.getNome() + " - CPF: " + cliente.getCpf())
				.corpo("email-analise.html")
				.variavel("cliente", cliente)
				.destinatario(cliente.getEmail()).build();
		envioEmailService.enviar(mensagem);		
	}
	
	public void constroiMensagemClienteAtivado(Cliente cliente, Conta conta) {
		
		var mensagem = Mensagem.builder()
				.assunto("Cadastro Aprovado")
				.corpo("email-aprovado.html")
				.variavel("cliente", cliente)
				.variavel("conta", conta)
				.destinatario(cliente.getEmail()).build();

		envioEmailService.enviar(mensagem);
	}
	
	public void constroiMensagemClienteRecusado(Cliente cliente) {
		
		var mensagem = Mensagem.builder()
				.assunto("Recusa do cliente: " + cliente.getNome() + " - CPF: " + cliente.getCpf())
				.corpo("email-recusado.html").variavel("cliente", cliente).destinatario(cliente.getEmail()).build();

		envioEmailService.enviar(mensagem);
	}
	
	public void constroiMensagemAnaliseExterna(Cliente cliente, String urlImagem) {
		
		var mensagem = Mensagem.builder()
				.assunto("Documento Para Análise")
				.corpo("email-externo.html")
				.variavel("cliente", cliente)
				.variavel("urlImagem", urlImagem)
				.destinatario("emailfornecedor@email.com.br").build();

		envioEmailService.enviar(mensagem);
	}
}
