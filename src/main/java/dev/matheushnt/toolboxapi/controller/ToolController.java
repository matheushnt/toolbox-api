package dev.matheushnt.toolboxapi.controller;

import dev.matheushnt.toolboxapi.dtos.ToolRequest;
import dev.matheushnt.toolboxapi.dtos.ToolResponse;
import dev.matheushnt.toolboxapi.entity.User;
import dev.matheushnt.toolboxapi.service.CreateToolService;
import dev.matheushnt.toolboxapi.service.GetToolsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tools")
@RequiredArgsConstructor
public class ToolController {

    private final CreateToolService createToolService;
    private final GetToolsService getToolsService;

    @PostMapping
    public ResponseEntity<ToolResponse> create(
        @Valid @RequestBody ToolRequest request,
        @AuthenticationPrincipal UserDetails principal
    ) {
        User user = (User) principal;
        return ResponseEntity.status(HttpStatus.CREATED).body(createToolService.execute(user, request));
    }

    @GetMapping
    public ResponseEntity<List<ToolResponse>> list(
        @RequestParam(required = false, name = "tag") String tag,
        @AuthenticationPrincipal UserDetails principal
    ) {
        User user = (User) principal;
        return ResponseEntity.status(HttpStatus.OK).body(getToolsService.execute(user, tag));
    }

}
