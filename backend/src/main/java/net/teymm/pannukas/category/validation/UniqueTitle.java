package net.teymm.pannukas.category.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.RECORD_COMPONENT, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueTitleValidator.class)
@Documented
public @interface UniqueTitle {

    String message() default "must be unique";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
