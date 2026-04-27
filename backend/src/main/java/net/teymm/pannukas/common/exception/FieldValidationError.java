package net.teymm.pannukas.common.exception;

import java.util.List;

public record FieldValidationError(
        String field,
        List<String> details
) {
}
