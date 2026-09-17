package com.fiap.campusride.validacao;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Validacao cruzada: o numero de vagas oferecidas nao pode ultrapassar a
 * capacidade maxima do tipo de veiculo informado (ex.: MOTO nao pode
 * oferecer 4 vagas).
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = VagasCompativeisComVeiculoValidator.class)
@Documented
public @interface VagasCompativeisComVeiculo {

    String message() default "O numero de vagas informado excede a capacidade do tipo de veiculo escolhido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}