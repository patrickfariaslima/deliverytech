package com.deliverytech.delivery_api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidCategoryValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCategory {
    String message() default "Invalid category. Allowed values are: Pizza, Hamburguer, Japonesa, Italiana, Vegetariana, Sobremesas";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
