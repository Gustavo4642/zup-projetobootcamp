package com.digital.banco.nosso.api.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.digital.banco.nosso.api.assembler.FotoClienteModelAssembler;
import com.digital.banco.nosso.api.model.FotoClienteModel;
import com.digital.banco.nosso.api.model.input.FotoClienteInput;
import com.digital.banco.nosso.domain.exception.EntidadeNaoEncontradaException;
import com.digital.banco.nosso.domain.model.Cliente;
import com.digital.banco.nosso.domain.model.FotoCliente;
import com.digital.banco.nosso.domain.service.CadastroClienteService;
import com.digital.banco.nosso.domain.service.CadastroFotoClienteService;
import com.digital.banco.nosso.domain.service.FotoStorageService;
import com.digital.banco.nosso.domain.service.FotoStorageService.FotoRecuperada;;

@RestController
@RequestMapping("/clientes/{codigoCliente}/foto")
public class FotoClienteController {

	@Autowired
	private CadastroClienteService cadastroCliente;
	
	@Autowired
	private CadastroFotoClienteService cadastroFoto;
	
	@Autowired
	private FotoClienteModelAssembler fotoClienteModelAssembler;
	
	@Autowired
	private FotoStorageService fotoStorage;
	
	// só é chamado se no consumo da API estiver com accept application JSON
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public FotoClienteModel buscar(@PathVariable String codigoCliente) {
	    FotoCliente fotoCliente = cadastroFoto.buscarOuFalhar(codigoCliente);
	    
	    return fotoClienteModelAssembler.toModel(fotoCliente);
	}
	
	// só é chamado se no consumo da API estiver com JPEG ou PNG
	@GetMapping
	public ResponseEntity<?> servirFoto(@PathVariable String codigoCliente,
				@RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
	    try {
			FotoCliente fotoCliente = cadastroFoto.buscarOuFalhar(codigoCliente);
			
			MediaType mediaTypeFoto = MediaType.parseMediaType(fotoCliente.getContentType());
			List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);
			
			verificarCompatibilidadeMediaType(mediaTypeFoto, mediaTypesAceitas);
			
			FotoRecuperada fotoRecuperada = fotoStorage.recuperar(fotoCliente.getNomeArquivo());
			
			if(fotoRecuperada.temUrl()) {
				// S3 
				return ResponseEntity
						.status(HttpStatus.FOUND)
						.header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
						.build();
			} else {
				
				// Local
				return ResponseEntity.ok()
						.contentType(mediaTypeFoto)
						.body(new InputStreamResource(fotoRecuperada.getInputStream()));				
			}
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto,
			List<MediaType> mediaTypesAceitas) throws HttpMediaTypeNotAcceptableException {

		// se qualquer uma das medias types forem iguais, true
		boolean compativel = mediaTypesAceitas.stream()
				.anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));
		
		if(!compativel) {
			// essa excpetion tem uma sobrecarga sobre lista de mediatypes
			throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
		}
	}

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public FotoClienteModel atualizarFoto(@PathVariable String codigoCliente,
			@Valid FotoClienteInput fotoClienteInput) throws IOException {
		
		Cliente cliente = cadastroCliente.buscarOuFalharCpf(codigoCliente);
		
		MultipartFile arquivo = fotoClienteInput.getArquivo();
		
		FotoCliente foto = new FotoCliente();
		foto.setCli(cliente);
		foto.setDescricao(fotoClienteInput.getDescricao());
		foto.setContentType(arquivo.getContentType());
		foto.setTamanho(arquivo.getSize());
		foto.setNomeArquivo(arquivo.getOriginalFilename());
		
		FotoCliente fotoSalva = cadastroFoto.salvar(foto, arquivo.getInputStream());
		
		return fotoClienteModelAssembler.toModel(fotoSalva);
	}
}
