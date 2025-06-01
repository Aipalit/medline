package com.medline.application.validation;

import br.com.caelum.stella.validation.CPFValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AppCPFValidator implements ConstraintValidator<CPF, String> {

    private CPFValidator stellaValidator;

    @Override
    public void initialize(CPF constraintAnnotation) {

        // Inicializa o validador do Caelum Stella
        // O 'false' indica que não queremos que ele lance exceções,
        // nós mesmos trataremos o resultado.
        this.stellaValidator = new CPFValidator(false);
    }

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        // 1. Se o CPF for nulo ou vazio, consideramos válido AQUI,
        // pois @NotBlank deve cuidar disso. Se não for nulo/vazio, validamos.
        if (cpf == null || cpf.trim().isEmpty()) {
            return true;
        }
        // 2. O Caelum Stella espera um CPF sem formatação.
        // removemos a formatação antes de validar.
        String cpfSemMascara = cpf.replaceAll("[^0-9]", "");

        // 3. Usa o validador do Caelum Stella.
        // O 'validate' retorna uma lista de erros. Se a lista estiver vazia, é válido.
        if (cpfSemMascara.isEmpty()) {
            return false;
        }
        return stellaValidator.invalidMessagesFor(cpfSemMascara).isEmpty();
    }

}
