package com.algaworks.algafood_api.core.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class PositivoOuZeroValidator implements ConstraintValidator<PositivoOuZero , Number> {

    @Override
    public boolean isValid(Number number, ConstraintValidatorContext constraintValidatorContext) {
        if (number != null) {
            return BigDecimal.valueOf(number.doubleValue()).compareTo(BigDecimal.ZERO) >= 0;
        }
        return false;
    }
}
