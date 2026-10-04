package dev.matheushnt.toolboxapi.dtos;

import dev.matheushnt.toolboxapi.enums.UserRole;

import java.util.UUID;

public record SignUpResponse(
    UUID id,
    String fullName,
    String email,
    UserRole role
) {
}
