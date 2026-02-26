package com.deliverytech.delivery_api.validation;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidWorkingHoursValidator implements ConstraintValidator<ValidWorkingHours, String> {
    private static final String HOURS_PATTERN = "^([0-1][0-9]|2[0-3]):[0-5][0-9]-([0-1][0-9]|2[0-3]):[0-5][0-9]$";
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    public void initialize(ValidWorkingHours constraintAnnotation) {
        // Método de inicialização
    }
    
    @Override
    public boolean isValid(String horario, ConstraintValidatorContext context) {
        if (horario == null || horario.isBlank()) {
            return true;
        }
        
        if (!horario.matches(HOURS_PATTERN)) {
            return false;
        }
        
        try {
            String[] partes = horario.split("-");
            LocalTime abertura = LocalTime.parse(partes[0], TIME_FORMATTER);
            LocalTime fechamento = LocalTime.parse(partes[1], TIME_FORMATTER);
            
            return fechamento.isAfter(abertura);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

}
