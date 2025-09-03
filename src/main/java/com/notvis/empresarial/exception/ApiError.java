package com.notvis.empresarial.exception;

import java.time.Instant;
import java.util.List;

public record ApiError (
        Instant timestamp,
        int status,
        String error,
        String message,
        String path,
        List<String> fieldErrors
) {}