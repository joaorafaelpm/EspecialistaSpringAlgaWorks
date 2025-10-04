package com.algaworks.algafood_api.core.validation;

import jakarta.validation.Constraint;
import jakarta.validation.OverridesAttribute;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Só vamos usa-la somente em classes, e não em propriedades de classes, por isso o ElementType.TYPE
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValorZeroIncluiDescricaoValidator.class})
public @interface ValorZeroIncluiDescricao {

    //    Substituindo o atributo menssagem da propriedade do PositiveOrZero pela nossa, para personalizar a anotação
    String message() default "descrição obrigatória inválida";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String valorField() ;
    String descricaoField() ;
    String descricaoObrigatoria() ;

}
