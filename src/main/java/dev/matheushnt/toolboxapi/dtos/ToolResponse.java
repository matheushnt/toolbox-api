package dev.matheushnt.toolboxapi.dtos;

import java.util.List;
import java.util.UUID;

public record ToolResponse(
    UUID id,
    String title,
    String link,
    String description,
    List<String> tags
) {
}
