package com.deliverytech.delivery_api.validation;

import jakarta.validation.ConstraintValidator;

public class ValidTelephoneValidator implements ConstraintValidator<ValidTelephone, String> {
    private static final String TELEPHONE_PATTERN = "^[1-9]{2}(?:9[0-9]{8}|[2-5][0-9]{7})$";

    @Override
    public void initialize(ValidTelephone constraintAnnotation) {
        // No initialization needed for this validator
    }

    @Override
    public boolean isValid(String telephone, jakarta.validation.ConstraintValidatorContext context) {
        if (telephone == null || telephone.isBlank()) {
            return true;
        }

        String telephoneNumbers = telephone.replaceAll("[^0-9]", "");
        return telephoneNumbers.matches(TELEPHONE_PATTERN);
    }

}
