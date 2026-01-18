package com.algaworks.algafood_api.core.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PositivoOuZeroValidator.class})
public @interface PositivoOuZero {

    //    Substituindo o atributo menssagem da propriedade do PositiveOrZero pela nossa, para personalizar a anotação
    String message() default "{PositivoOuZero.invalida}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
