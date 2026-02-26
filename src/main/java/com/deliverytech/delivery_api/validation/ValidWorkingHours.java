package com.deliverytech.delivery_api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidWorkingHoursValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidWorkingHours {
    String message() default "Invalid working hours format. Expected format:HH:MM-HH:MM (ex: 08:00-22:00)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
