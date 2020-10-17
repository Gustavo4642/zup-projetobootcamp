package com.digital.banco.nosso.core.validation;

import java.time.OffsetDateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MaiorIdadeValidator implements ConstraintValidator<MaiorIdade, OffsetDateTime> {

	private OffsetDateTime dataAtual;

	@Override
	public void initialize(MaiorIdade constraintAnnotation) {
		this.dataAtual = OffsetDateTime.now();
	}

	@Override
	public boolean isValid(OffsetDateTime value, ConstraintValidatorContext context) {

		boolean valido = false;

		if (value != null) {

			int ano = this.dataAtual.getYear() - value.getYear();
			int mes = this.dataAtual.getMonthValue() - value.getMonthValue();
			int dia = this.dataAtual.getDayOfMonth() - value.getDayOfMonth();

			// testa ano
			if (ano > 18) {
				valido = true;

			} else if (ano == 18) {

				// testa mes
				if (mes > 0) {
					valido = true;
				} else if (mes == 0) {
					
					//testa dia
					if (dia >= 0) {
						valido = true;
					} else {
						
						// dia inválido
						valido = false;
					}

				} else {
					// mes inválido
					valido = false;
				}

			} else {
				// ano inválido
				valido = false;
			}

		}

		return valido;
	}

}
