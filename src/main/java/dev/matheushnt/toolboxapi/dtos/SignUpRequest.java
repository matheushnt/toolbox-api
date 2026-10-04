package dev.matheushnt.toolboxapi.dtos;

import dev.matheushnt.toolboxapi.validation.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignUpRequest(
    @NotBlank(message = "Field [fullName] is required")
    String fullName,

    @NotBlank(message = "Field [email] is required")
    @Email(message = "Email address is invalid", regexp = "^\\S+@\\S+\\.\\S+$")
    String email,

    @NotBlank(message = "Field [password] is required")
    @Password
    String password
) {
}
