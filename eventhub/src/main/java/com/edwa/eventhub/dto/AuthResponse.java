package com.edwa.eventhub.dto;

public record AuthResponse(
        String token,
        String role,
        String fullName
) {}