package net.teymm.pannukas.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.RECORD_COMPONENT, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidImageValidator.class)
@Documented
public @interface ValidImage {

    String message() default "must be a JPEG, PNG, or WebP file no larger than 5 MB";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
