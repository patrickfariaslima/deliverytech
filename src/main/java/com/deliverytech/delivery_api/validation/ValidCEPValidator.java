package com.deliverytech.delivery_api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidCEPValidator implements ConstraintValidator<ValidCEP, String> {
    private static final String CEP_PATTERN = "^\\d{5}-?\\d{3}$";

    @Override
    public void initialize(ValidCEP constraintAnnotation){
        // No initialization needed for this validator
    }

    @Override
    public boolean isValid(String cep, ConstraintValidatorContext context) {
        if (cep == null || cep.isBlank()) {
            return true;
        }
        return cep.matches(CEP_PATTERN);
    }
}
