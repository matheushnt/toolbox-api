package dev.matheushnt.toolboxapi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ToolRequest(
    @NotBlank(message = "Field [title] is required")
    String title,

    @NotBlank(message = "Field [link] is required")
    String link,

    @NotBlank(message = "Field [description] is required")
    String description,

    @NotNull(message = "Field [tags] is required")
    List<@NotBlank(message = "Field [tags] cannot contain blank values") String> tags
) {
}
