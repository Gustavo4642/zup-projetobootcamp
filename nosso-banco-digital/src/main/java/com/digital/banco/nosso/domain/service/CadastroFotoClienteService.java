package com.digital.banco.nosso.domain.service;

import java.io.InputStream; 
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digital.banco.nosso.domain.exception.FotoClienteNaoEncontradaException;
import com.digital.banco.nosso.domain.model.FotoCliente;
import com.digital.banco.nosso.domain.repository.ClienteRepository;
import com.digital.banco.nosso.domain.service.FotoStorageService.NovaFoto;;

@Service
public class CadastroFotoClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private FotoStorageService fotoStorage;
	
	@Transactional
	public FotoCliente salvar(FotoCliente foto, InputStream dadosArquivo) {
		
		String codigoCliente = foto.getCliId();
		String nomeNovoArquivo = fotoStorage.gerarNomeArquivo(foto.getNomeArquivo());
		String nomeArquivoExistente = null;
		
		// antes de salvar a foto do produto, verifica se existe, exclui se existir e inclui outra.
		Optional<FotoCliente> fotoExistente = clienteRepository.findFotoById(codigoCliente);
		if(fotoExistente.isPresent()) {
			nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
			clienteRepository.delete(fotoExistente.get());
		}
		
		// aplicando novo nome no arquivo
		foto.setNomeArquivo(nomeNovoArquivo);
		
		// Passo 1 salvo a foto no repositorio
		foto = clienteRepository.save(foto);
		// descarregar a fila para não dar problema no armazenamento da foto
		clienteRepository.flush(); 
		
		// Passo 2 salvo a foto no storage
		NovaFoto novaFoto = NovaFoto.builder()
				.nomeArquivo(foto.getNomeArquivo())
				.contentType(foto.getContentType())
				.inputStream(dadosArquivo)
				.build();
		
		fotoStorage.substituir(nomeArquivoExistente, novaFoto);
		
		// se der rollback, não fica gravado só no banco
		return foto;
	}
	

	public FotoCliente buscarOuFalhar(String codigoCliente) {
	    return clienteRepository.findFotoById(codigoCliente)
	            .orElseThrow(() -> new FotoClienteNaoEncontradaException(codigoCliente));
	}      

	
}
