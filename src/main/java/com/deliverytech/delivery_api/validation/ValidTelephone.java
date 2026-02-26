package com.deliverytech.delivery_api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidTelephoneValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTelephone {
    String message() default "Invalid Telephone number format: DDNNNNNNNNN (10 or 11 digits)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
