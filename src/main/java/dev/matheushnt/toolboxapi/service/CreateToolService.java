package dev.matheushnt.toolboxapi.service;

import dev.matheushnt.toolboxapi.dtos.ToolRequest;
import dev.matheushnt.toolboxapi.dtos.ToolResponse;
import dev.matheushnt.toolboxapi.entity.Tag;
import dev.matheushnt.toolboxapi.entity.Tool;
import dev.matheushnt.toolboxapi.entity.User;
import dev.matheushnt.toolboxapi.repository.TagRepository;
import dev.matheushnt.toolboxapi.repository.ToolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CreateToolService {

    private final ToolRepository toolRepository;
    private final TagRepository tagRepository;

    @Transactional
    public ToolResponse execute(User user, ToolRequest dto) {
        Tool tool = new Tool();
        tool.setTitle(dto.title());
        tool.setLink(dto.link());
        tool.setDescription(dto.description());
        tool.setUser(user);

        if (!dto.tags().isEmpty()) {
            for (String tagName : dto.tags()) {
                Tag tag = tagRepository.findByName(tagName)
                    .orElseGet(() -> {
                        Tag newTag = new Tag();
                        newTag.setName(tagName);
                        return tagRepository.save(newTag);
                    });

                tool.addTag(tag);
            }
        }

        toolRepository.save(tool);

        return new ToolResponse(
            tool.getId(),
            tool.getTitle(),
            tool.getLink(),
            tool.getDescription(),
            toList(tool.getTags())
        );
    }

    private List<String> toList(Set<Tag> tags) {
        return tags.stream()
            .map(Tag::getName)
            .toList();
    }

}
