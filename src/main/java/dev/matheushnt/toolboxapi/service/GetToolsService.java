package dev.matheushnt.toolboxapi.service;

import dev.matheushnt.toolboxapi.dtos.ToolResponse;
import dev.matheushnt.toolboxapi.entity.Tag;
import dev.matheushnt.toolboxapi.entity.Tool;
import dev.matheushnt.toolboxapi.repository.ToolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetToolsService {

    private final ToolRepository repository;

    public List<ToolResponse> execute(UUID userId, String tagName) {
        List<Tool> tools;

        if (tagName != null) {
            tools = repository.findByUserIdAndTagName(userId, tagName);
        } else {
            tools = repository.findAll();
        }

        return toToolResponseList(tools);
    }

    private List<ToolResponse> toToolResponseList(List<Tool> tools) {
        return tools.stream()
            .map((tag) -> new ToolResponse(tag.getId(), tag.getTitle(), tag.getLink(), tag.getDescription(), toTagNameList(tag.getTags())))
            .toList();
    }

    private List<String> toTagNameList(Set<Tag> tags) {
        return tags.stream()
            .map(Tag::getName)
            .toList();
    }

}
