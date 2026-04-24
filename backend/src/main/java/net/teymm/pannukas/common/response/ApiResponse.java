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
    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public static class Builder<T> {
        private String path;
        private int statusCode;
        private String status;
        private String message;
        private T data;
        private Object errors;
        private Instant timestamp;

        public Builder<T> path(String path) {
            this.path = path;
            return this;
        }

        public Builder<T> statusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder<T> status(String status) {
            this.status = status;
            return this;
        }

        public Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        public Builder<T> errors(Object errors) {
            this.errors = errors;
            return this;
        }

        public Builder<T> timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ApiResponse<T> build() {
            return new ApiResponse<>(
                    path,
                    statusCode,
                    status,
                    message,
                    data,
                    errors,
                    timestamp
            );
        }
    }
}
