package net.teymm.pannukas.common.response;

import java.time.Instant;

public record ApiResponse<T>(
        String path,
        int statusCode,
        String status,
        String message,
        T data,
        Object errors,
        Instant timestamp
) {
}
