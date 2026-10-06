package dev.matheushnt.toolboxapi.controller;

import dev.matheushnt.toolboxapi.documentation.ToolControllerApi;
import dev.matheushnt.toolboxapi.dtos.ToolRequest;
import dev.matheushnt.toolboxapi.dtos.ToolResponse;
import dev.matheushnt.toolboxapi.entity.User;
import dev.matheushnt.toolboxapi.service.CreateToolService;
import dev.matheushnt.toolboxapi.service.DeleteToolService;
import dev.matheushnt.toolboxapi.service.GetToolsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tools")
@RequiredArgsConstructor
public class ToolController implements ToolControllerApi {

    private final CreateToolService createToolService;
    private final GetToolsService getToolsService;
    private final DeleteToolService deleteToolService;

    @PostMapping
    @Override
    public ResponseEntity<ToolResponse> create(
        @Valid @RequestBody ToolRequest request,
        @AuthenticationPrincipal UserDetails principal
    ) {
        User user = (User) principal;
        return ResponseEntity.status(HttpStatus.CREATED).body(createToolService.execute(user, request));
    }

    @GetMapping
    @Override
    public ResponseEntity<List<ToolResponse>> list(
        @RequestParam(required = false, name = "tag") String tag,
        @AuthenticationPrincipal UserDetails principal
    ) {
        User user = (User) principal;
        return ResponseEntity.status(HttpStatus.OK).body(getToolsService.execute(user.getId(), tag));
    }

    @DeleteMapping("/{toolId}")
    @Override
    public ResponseEntity<Void> delete(
        @PathVariable UUID toolId,
        @AuthenticationPrincipal UserDetails principal
    ) {
        User user = (User) principal;
        deleteToolService.execute(user.getId(), toolId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
