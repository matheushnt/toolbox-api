package dev.matheushnt.toolboxapi.service;

import dev.matheushnt.toolboxapi.exception.ResourceNotFoundException;
import dev.matheushnt.toolboxapi.repository.ToolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteToolService {

    private final ToolRepository repository;

    @Transactional
    public void execute(UUID userId, UUID toolId) {
        int deleted = repository.deleteByIdAndUserId(toolId, userId);

        if (deleted == 0) {
            throw new ResourceNotFoundException("Tool not found");
        }
    }

}
