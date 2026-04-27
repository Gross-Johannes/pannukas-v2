package net.teymm.pannukas.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class EnumValueValidator implements ConstraintValidator<EnumValue, String> {

    private Set<String> acceptedValues;
    private boolean ignoreCase;
    private String allowedValuesMessage;

    @Override
    public void initialize(EnumValue constraintAnnotation) {
        ignoreCase = constraintAnnotation.ignoreCase();

        List<String> enumValues = Arrays.stream(constraintAnnotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .toList();

        acceptedValues = enumValues.stream()
                .map(value -> ignoreCase ? value.toLowerCase(Locale.ROOT) : value)
                .collect(Collectors.toSet());

        allowedValuesMessage = "must be one of: " + String.join(", ", enumValues);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true; // Let @NotBlank handle this case
        }

        String candidate = ignoreCase ? value.toLowerCase(Locale.ROOT) : value;

        if (acceptedValues.contains(candidate)) {
            return true;
        }

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(allowedValuesMessage)
                .addConstraintViolation();

        return false;
    }
}
