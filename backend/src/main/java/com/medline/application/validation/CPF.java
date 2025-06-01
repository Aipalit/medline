package com.medline.application.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = AppCPFValidator.class) // Aponta para a classe que fará a validação
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER }) // onde sera
                                                                                                       // usada
@Retention(RetentionPolicy.RUNTIME) // Garante que a anotação esteja disponível em tempo de execução

public @interface CPF {

    // Mensagem padrão caso a validação falhe

    String message() default "CPF Inválido";

    // Padrão para validação - não mexer
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
