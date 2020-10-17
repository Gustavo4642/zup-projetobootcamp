package com.digital.banco.nosso.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digital.banco.nosso.domain.exception.ClienteNaoEncontradoException;
import com.digital.banco.nosso.domain.exception.NegocioException;
import com.digital.banco.nosso.domain.model.Cliente;
import com.digital.banco.nosso.domain.repository.ClienteRepository;

@Service
public class CadastroClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Transactional
	public Cliente salvar(Cliente cliente) {

		buscarPorEmail(cliente.getEmail());
		buscarPorCpf(cliente.getCpf());

		if (!validaPontuacaoCpf(cliente)) {
			throw new NegocioException("Número do registro de contribuinte individual brasileiro (CPF) inválido");
		}
		
		return clienteRepository.save(cliente);
	}

	// validacao para pequisa
	public Cliente buscarOuFalharCpf(String cpfCliente) {

		return clienteRepository.findByCpf(cpfCliente)
				.orElseThrow(() -> new ClienteNaoEncontradoException(cpfCliente));

	}

	// validação para inclusão.
	public void buscarPorEmail(String emailCliente) {

		Cliente clienteEncontrado = clienteRepository.procurePorEmail(emailCliente);

		if (clienteEncontrado != null && clienteEncontrado.getEmail().equals(emailCliente)) {
			throw new NegocioException(String.format("Já existe um cliente cadastrado com o e-mail %s", emailCliente));
		}
	}

	// validação para inclusão.
	public void buscarPorCpf(String cpfCliente) {

		Cliente clienteEncontrado = clienteRepository.procurePorCpf(cpfCliente);

		if (clienteEncontrado != null && clienteEncontrado.getCpf().equals(cpfCliente)) {
			throw new NegocioException(String.format("Já existe um cliente cadastrado com o CPF %s", cpfCliente));
		}
	}

	public boolean validaPontuacaoCpf(Cliente cliente) {

		boolean valido = false;

		// se o cpf não tiver com a pontuação, o tamanho do número é menor que 14
		if (cliente.getCpf().length() != 14) {
			valido = false;
		} else {
			int contador = 0;

			// senão, iremos ler o número inteiro
			for (int i = 0; i < cliente.getCpf().length(); i++) {
				if (i == 3 && cliente.getCpf().substring(3, 3 + 1).equals(".")) {
					contador++;
					continue;
				}
				if (i == 7 && cliente.getCpf().substring(7, 7 + 1).equals(".")) {

					contador++;
					continue;
				}
				if (i == 11 && cliente.getCpf().substring(11, 11 + 1).equals("-")) {

					contador++;
					continue;
				}
			}
			if (contador == 3) {
				valido = true;
			}
		}

		return valido;
	}

}
