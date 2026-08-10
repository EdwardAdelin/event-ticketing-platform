package com.edwa.eventhub.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        List<String> validationErrors // populated only if DTO validations fail
) {}