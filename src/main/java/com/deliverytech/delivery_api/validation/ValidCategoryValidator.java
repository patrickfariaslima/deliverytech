package com.deliverytech.delivery_api.validation;

import java.util.Arrays;
import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidCategoryValidator implements ConstraintValidator<ValidCategory, String> {
    
    private static final List<String> CATEGORIES_ALLOWED = Arrays.asList(
        "Pizza", "Hamburguer", "Japonesa", "Italiana", "Vegetariana", "Sobremesas"
    );
    
    @Override
    public void initialize(ValidCategory constraintAnnotation) {
        // Método de inicialização
    }
    
    @Override
    public boolean isValid(String category, ConstraintValidatorContext context) {
        if (category == null || category.isBlank()) {
            return true; 
        }
        return CATEGORIES_ALLOWED.contains(category);
    }
}   